package be.technifuture.tff.service

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.GpsCoordinates
import kotlin.random.Random

class ChatService {

    private val listChat = mutableListOf(
        Chat("34",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtBaRjzOh_O7tNM-cSSFfOYhb0qV-klLIZym0Tmuelo2rWaRoxRNZgmCvWnaDa-UOR3C4&usqp=CAU",
        "Catnip",
            40,
            100,
            4,
            true,
            GpsCoordinates(
            Random.nextDouble(
                -0.03,
                0.03),
            Random.nextDouble(
                -0.03,
                0.03)
            )
        ),
        Chat("29",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtBaRjzOh_O7tNM-cSSFfOYhb0qV-klLIZym0Tmuelo2rWaRoxRNZgmCvWnaDa-UOR3C4&usqp=CAU",
            "Catnip",
            40,
            100,
            4,
            true,
            GpsCoordinates(
                Random.nextDouble(
                    -0.03,
                    0.03),
                Random.nextDouble(
                    -0.03,
                    0.03)
            )
        ),
    )
/*
    fun getClan(): MutableList<ClanModel> {
        return listOfClan
    }

    fun getClanById(idClan: Int): ClanModel {
        return listOfClan[idClan-1]
    }
    */

}