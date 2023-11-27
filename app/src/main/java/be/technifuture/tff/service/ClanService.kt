package be.technifuture.tff.service

import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel

class ClanService {
    fun getClan(): MutableList<ClanModel> {
        return mutableListOf(
            ClanModel(1, "Acharme", "clan_jaune", "Bonus à l'expension"),
            ClanModel(2, "Flachato", "clan_rouge", "Bonus à l'attaque"),
            ClanModel(3, "Tocaxik", "clan_vert", "Resistance au attaque")
        )
    }

}