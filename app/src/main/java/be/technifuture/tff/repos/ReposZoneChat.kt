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

        nearChats.clear()
        zoneChats.forEach { zoneChat ->

            val distance: Double = SphericalUtil.computeDistanceBetween(
                CoordinatesUser!!.toLatLng(), zoneChat.gpsCoordinates.toLatLng()
            )

            Log.d("LM","distance = " + distance.toString() + " raduis = " + zoneChat.radius.toString())

            if (distance <= zoneChat.radius) {
                nearChats.add(zoneChat)
            }

            gpsCoordinatesTarget = zoneChat.chat.gpsCoordinates
        }

        return gpsCoordinatesTarget
    }

    fun mockData(longitude: Float, latitude: Float) {
        if(zoneChats.count() == 0) {
            zoneChats.clear()
            //val chats = mutableListOf<ZoneChat>()
            val centerLatitude = latitude
            val centerLongitude = longitude
            val maxRadius = 1500.0

            for (i in 1..50) {

                val randomLatitude = centerLatitude + kotlin.random.Random.nextDouble(
                    -0.03,
                    0.03
                ) // Ajustez la plage de latitude
                val randomLongitude = centerLongitude + kotlin.random.Random.nextDouble(
                    -0.03,
                    0.03
                ) // Ajustez la plage de longitude


                var urlChat: String;
                var chatRGB: ChatRGB;
                if (i % 2 == 0) {
                    chatRGB = ChatRGB(255, 0, 0);
                    urlChat =
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtBaRjzOh_O7tNM-cSSFfOYhb0qV-klLIZym0Tmuelo2rWaRoxRNZgmCvWnaDa-UOR3C4&usqp=CAU"

                } else if (i % 3 == 0) {
                    chatRGB = ChatRGB(0, 255, 0);
                    urlChat =
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZHgn6X6Do3JTCY5yCgjFUeZHjZ8PYQFQpAV2dZyEFhTTiGa8jvUoGONlwmufaQsca3JA&usqp=CAU"

                } else {
                    chatRGB = ChatRGB(0, 0, 255);
                    urlChat =
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAh1WWuVjtXHFPVQ1VLL8_hpAONGCyxaVTA4jgOb92khrHXoLZNJ1kRGGE1ccPLraT0GE&usqp=CAU"
                }

                val zoneChat = ZoneChat(
                    id = "Zone_$i",
                    nom = "Zone $i",
                    radius = 0,
                    color = chatRGB,
                    gpsCoordinates = GpsCoordinates(randomLatitude, randomLongitude),
                    chat = Chat(
                        id = "Chat_$i",
                        urlImage = urlChat,
                        nom = "Chat $i",
                        vie = kotlin.random.Random.nextInt(1, 100),
                        maxVie = 0,
                        level = kotlin.random.Random.nextInt(1, 10),
                        isVisible = true,
                        gpsCoordinates = GpsCoordinates(randomLatitude, randomLongitude),
                        0
                    )
                )

                zoneChat.radius = zoneChat.chat.level * 50
                zoneChat.chat.maxVie =
                    50 * Math.pow(2.toDouble(), zoneChat.chat.level.toDouble() - 1).toInt()
                zoneChat.chat.vie = Random.nextInt(1, zoneChat.chat.maxVie)
                zoneChats.add(zoneChat)
            }
            Log.d("LM", zoneChats.toString())
        }
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