package be.technifuture.tff.repos

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ChatRGB
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.ZoneChat
import kotlin.random.Random

class ReposZoneChat  {

    fun mockData(longitude : Float, latitude : Float): MutableList<ZoneChat> {
        val baseLongitude = longitude
        val baseLatitude = latitude
        val itemsChatShow = mutableListOf<ZoneChat>()

        for (i in 1..10) { // Créez 10 objets Chat (vous pouvez ajuster le nombre)
            val randomLatitude = baseLatitude + Random.nextDouble(-0.03, 0.03) // Ajustez la plage de latitude
            val randomLongitude = baseLongitude + Random.nextDouble(-0.03, 0.03) // Ajustez la plage de longitude
            val randomRadius = Random.nextInt(1, 1001) // Rayon aléatoire entre 1 et 2000 mètres

            val zoneChat = ZoneChat(
                id = "ZoneChat $i",
                nom = "ZoneChat $i",
                radius = randomRadius,
                color = ChatRGB(0,0,255),
                gpsCoordinates = GpsCoordinates(randomLatitude, randomLongitude)
            )

            itemsChatShow.add(zoneChat)
        }

        return itemsChatShow
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