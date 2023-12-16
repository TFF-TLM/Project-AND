package be.technifuture.tff.repos

import android.util.Log
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ChatRGB
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.ZoneChat
import com.google.maps.android.SphericalUtil
import kotlin.random.Random

class ReposZoneChat  {

    public var zoneChats = mutableListOf<ZoneChat>()
    public var nearChats = mutableListOf<ZoneChat>()

    public fun getDistance(source:GpsCoordinates, destination:GpsCoordinates): Double{
        val distance: Double = SphericalUtil.computeDistanceBetween(
            source!!.toLatLng(), destination.toLatLng()
        )
        return distance
    }

    public fun getNearChat(CoordinatesUser:GpsCoordinates): GpsCoordinates? {

        var gpsCoordinatesTarget: GpsCoordinates? = null
        var TempDistance = 10000.0

        nearChats.clear()
        zoneChats.forEach { zoneChat ->


            val distance: Double = SphericalUtil.computeDistanceBetween(
                CoordinatesUser!!.toLatLng(), zoneChat.gpsCoordinates!!.toLatLng()
            )

            if (distance <= zoneChat.radius) {
                nearChats.add(zoneChat)
            }

            if(distance < TempDistance) {
                TempDistance = distance
                gpsCoordinatesTarget = zoneChat.chat.gpsCoordinates
            }

        }

        return gpsCoordinatesTarget
    }

    companion object {

        private var instance: ReposZoneChat? = null

        fun getInstance(): ReposZoneChat {
            if (instance == null) {
                instance = ReposZoneChat()
            }
            return instance as ReposZoneChat
        }
    }
}