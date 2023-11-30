package be.technifuture.tff.repos

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ChatRGB
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.PointInteret
import kotlin.random.Random

class ReposPointInteret {

    fun mockData(longitude : Float, latitude : Float): MutableList<PointInteret> {
        val baseLongitude = longitude
        val baseLatitude = latitude
        val itemsPointInteretShow = mutableListOf<PointInteret>()

        for (i in 1..10) { // Créez 10 objets Chat (vous pouvez ajuster le nombre)
            val randomLatitude = baseLatitude + Random.nextDouble(-0.03, 0.03) // Ajustez la plage de latitude
            val randomLongitude = baseLongitude + Random.nextDouble(-0.03, 0.03) // Ajustez la plage de longitude

            val pointInteret = PointInteret(
                id = "PointInteret$i",
                nom = "PointInteret $i",
                bonusLevel = 100,
                bonusType = 1,
                isVisible = true,
                gpsCoordinates = GpsCoordinates(randomLatitude, randomLongitude)
            )
            itemsPointInteretShow.add(pointInteret)
        }
        return itemsPointInteretShow
    }

    companion object {
        private var instance: ReposPointInteret? = null
        fun getInstance(): ReposPointInteret {
            if (instance == null) {
                instance = ReposPointInteret()
            }
            return instance as ReposPointInteret
        }
    }

}