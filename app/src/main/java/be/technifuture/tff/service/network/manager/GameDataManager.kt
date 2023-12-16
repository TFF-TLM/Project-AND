package be.technifuture.tff.service.network.manager

import android.location.Location
import android.util.Log
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.PointInteret
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.model.ZoneChat
import be.technifuture.tff.repos.toLatLng
import be.technifuture.tff.service.network.dto.Cat
import be.technifuture.tff.service.network.dto.CatInBagResponse
import be.technifuture.tff.service.network.dto.CatOnMapResponse
import be.technifuture.tff.service.network.dto.CatWithInteract
import be.technifuture.tff.service.network.dto.ErrorDetailsResponse
import be.technifuture.tff.service.network.service.CatApiServiceImpl
import be.technifuture.tff.service.network.service.InteractApiServiceImpl
import be.technifuture.tff.service.network.utils.CallBuilder
import com.google.gson.Gson
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class GameDataManager {
    private val interactService = InteractApiServiceImpl.instance
    private val catService = CatApiServiceImpl.instance

    private var catFromUserInBag: List<ZoneChat> = listOf()
    private var catFromUserOnMap: List<ZoneChat> = listOf()
    private var catOnMap: List<ZoneChat> = listOf()

    companion object {
        val instance: GameDataManager by lazy {
            GameDataManager()
        }
    }

    fun getCatFromUserInBag(
        handler: (cats: List<ZoneChat>?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { catService.catInBag() },
            null,
            Nothing::class.java
        ) { callResponse, _, code ->
            val result = callResponse?.toZoneChatList()
            result?.let {
                catFromUserInBag = it
            }
            handler(result, code)
        }
    }

    fun getCatFromUserOnMap(
        handler: (cats: List<ZoneChat>?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { catService.catOnMap() },
            null,
            Nothing::class.java
        ) { callResponse, _, code ->
            val result = callResponse?.toZoneChatList()
            result?.let {
                catFromUserOnMap = it
            }
            handler(result, code)
        }
    }

    fun getCatById(
        id: Int,
        handler: (cat: ZoneChat?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { catService.cat(id) },
            404,
            ErrorDetailsResponse::class.java
        ) { callResponse, error, code ->
            handler(callResponse?.toZoneChat(), error, code)
        }
    }

    fun getSurroundings(
        lat: Float,
        lon: Float,
        handler: (cats: List<ZoneChat>?, points: List<PointInteret>?, code: Int) -> Unit
    ) {
        CallBuilder.getCall(
            { interactService.surroundings(lat, lon) },
            null,
            Nothing::class.java
        ) { callResponse, _, code ->
            var cats: List<ZoneChat>? = null
            var points: List<PointInteret>? = null

            callResponse?.let {
                cats = it.toZoneChatList()
                catOnMap = it.toZoneChatList()
                points = it.toPointInteretList()
            }

            handler(cats, points, code)
        }
    }

    fun getNearCats(lat: Float, lon: Float): List<ZoneChat> {
        val nearCats: MutableList<ZoneChat> = mutableListOf()
        catOnMap.forEach { zoneChat ->
            zoneChat.gpsCoordinates?.let {
                val results = FloatArray(1)
                Location.distanceBetween(
                    lat.toDouble(),
                    lon.toDouble(),
                    it.latitude,
                    it.longitude,
                    results
                )
                if (results[0] <= zoneChat.radius) {
                    nearCats.add(zoneChat)
                }
            }
        }
        return nearCats
    }

    fun getNearestCats(lat: Float, lon: Float): ZoneChat? {
        var nearestCat: ZoneChat? = null
        var nearestDistance: Float? = null
        catOnMap.forEach { zoneChat ->
            zoneChat.gpsCoordinates?.let {
                val results = FloatArray(1)
                Location.distanceBetween(
                    lat.toDouble(),
                    lon.toDouble(),
                    it.latitude,
                    it.longitude,
                    results
                )
                if (results[0] <= zoneChat.radius) {
                    if (nearestDistance == null) {
                        nearestDistance = results[0]
                        nearestCat = zoneChat
                    } else {
                        nearestDistance?.let { dist ->
                            if (dist > results[0]) {
                                nearestDistance = results[0]
                                nearestCat = zoneChat
                            }
                        }
                    }

                }
            }
        }
        return nearestCat
    }

    fun refreshDataGameFromUser(
        id: Int = AuthDataManager.instance.user.id,
        handler: (
            user: UserModel?,
            catsFromUserInBag: List<ZoneChat>?,
            catsFromUserOnMap: List<ZoneChat>?,
            error: ErrorDetailsResponse?,
            codeUser: Int,
            codeInBag: Int,
            codeOnMap: Int
        ) -> Unit
    ) {
        AuthDataManager.instance.getUserDetailsById(id) { user, errorUser, codeUser ->
            getCatFromUserInBag { catsInBag, codeInBag ->
                getCatFromUserOnMap { catsOnMap, codeOnMap ->
                    handler(user, catsInBag, catsOnMap, errorUser, codeUser, codeInBag, codeOnMap)
                }
            }
        }
    }
}