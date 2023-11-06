package be.technifuture.tff.repos

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ChatRGB
import be.technifuture.tff.model.GpsCoordinates
import kotlin.random.Random

class ReposChat {

    fun mockData(): MutableList<Chat> {
        val itemsChatShow = mutableListOf<Chat>()
        val baseLongitude = 5.5314775
        val baseLatitude = 50.6128178

        for (i in 1..10) { // Créez 10 objets Chat (vous pouvez ajuster le nombre)
            val randomLatitude = baseLatitude + Random.nextDouble(-0.03, 0.03) // Ajustez la plage de latitude
            val randomLongitude = baseLongitude + Random.nextDouble(-0.03, 0.03) // Ajustez la plage de longitude
            val randomRadius = Random.nextInt(1, 1001) // Rayon aléatoire entre 1 et 2000 mètres

            val chat = Chat(
                id = "Chat$i",
                urlImage = "URL_de_l'image",
                nom = "Chat $i",
                vie = 100,
                level = 1,
                radius = randomRadius,
                color = ChatRGB(0,0,255),
                gpsCoordinates = GpsCoordinates(randomLatitude, randomLongitude)
            )

            itemsChatShow.add(chat)
        }

        return itemsChatShow
    }

    companion object {

        private var instance: ReposChat? = null

        fun getInstance(): ReposChat {
            if (instance == null) {
                instance = ReposChat()
            }
            return instance as ReposChat
        }


    }

}