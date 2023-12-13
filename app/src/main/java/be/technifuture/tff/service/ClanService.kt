package be.technifuture.tff.service

import be.technifuture.tff.R
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel

class ClanService {

    private val listOfClan = mutableListOf(
        ClanModel(1, "Achamer", R.drawable.clan_jaune),
        ClanModel(2, "Flachato", R.drawable.clan_rouge),
        ClanModel(3, "Tochaxik", R.drawable.clan_vert)
    )

    fun getClan(): MutableList<ClanModel> {
        return listOfClan
    }

    fun getClanById(idClan: Int): ClanModel{
        return listOfClan[idClan-1]
    }

}