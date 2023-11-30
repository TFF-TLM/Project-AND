package be.technifuture.tff.service

import be.technifuture.tff.R
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel

class ClanService {

    private val listOfClan = mutableListOf(
        ClanModel(1, "Achamer", R.drawable.clan_jaune, "Bonus à l'expension"),
        ClanModel(2, "Flachato", R.drawable.clan_rouge, "Bonus à l'attaque"),
        ClanModel(3, "Tochaxik", R.drawable.clan_vert, "Resistance au attaque")
    )

    fun getClan(): MutableList<ClanModel> {
        return listOfClan
    }

    fun getClanById(idClan: Int): ClanModel{
        return listOfClan[idClan-1]
    }

}