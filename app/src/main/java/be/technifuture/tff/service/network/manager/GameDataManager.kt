package be.technifuture.tff.service.network.manager

import be.technifuture.tff.service.network.dto.Cat
import be.technifuture.tff.service.network.dto.CatInBagResponse
import be.technifuture.tff.service.network.dto.CatOnMapResponse
import be.technifuture.tff.service.network.dto.CatWithInteract
import be.technifuture.tff.service.network.dto.ErrorDetailsResponse
import be.technifuture.tff.service.network.service.CatApiServiceImpl
import be.technifuture.tff.service.network.service.InteractApiServiceImpl
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class GameDataManager {
    private val interactService = InteractApiServiceImpl.instance
    private val catService = CatApiServiceImpl.instance

    var catInBag: List<Cat> = mutableListOf()
    var catOnMap: List<CatWithInteract> = mutableListOf()

    companion object {
        val instance: GameDataManager by lazy {
            GameDataManager()
        }
    }

    private fun <T, K> getCatCall(
        call: suspend () -> Response<T>,
        catchError: Int?,
        errorType: Class<K>?,
        handler: (T?, K?, Int) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = call()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { callResponse ->
                            handler(callResponse, null, response.code())
                        }
                    } else {
                        var errorResponse: K? = null
                        catchError?.let { codeError ->
                            if (response.code() == codeError) {
                                errorResponse = Gson().fromJson(
                                    response.body().toString(),
                                    errorType
                                )
                            }
                        }
                        handler(null, errorResponse, response.code())
                    }
                } catch (e: HttpException) {
                    handler(null, null, response.code())
                    print(e)
                } catch (e: Throwable) {
                    handler(null, null, response.code())
                    print(e)
                }
            }
        }
    }

    fun getCatInBag(
        handler: (cats: CatInBagResponse?, code: Int) -> Unit
    ) {
        getCatCall(
            { catService.catInBag() },
            null,
            Nothing::class.java
        ) { callResponse, _, code ->
            callResponse?.let {
                catInBag = it.cats
            }
            handler(callResponse, code)
        }
    }

    fun getCatOnBag(
        handler: (cats: CatOnMapResponse?, code: Int) -> Unit
    ) {
        getCatCall(
            { catService.catOnMap() },
            null,
            Nothing::class.java
        ) { callResponse, _, code ->
            callResponse?.let {
                catOnMap = it.cats
            }
            handler(callResponse, code)
        }
    }

    fun getCatById(
        id: Int,
        handler: (cat: CatWithInteract?, error: ErrorDetailsResponse?, code: Int) -> Unit
    ) {
        getCatCall(
            { catService.cat(id) },
            404,
            ErrorDetailsResponse::class.java
        ) { callResponse, error, code ->
            handler(callResponse, error, code)
        }
    }
}