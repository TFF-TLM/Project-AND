package be.technifuture.tff.repos

import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.network.utils.ClanBuilder

class ReposUser {
    private var user: UserModel? = null

    fun getChatNb() : Int {
       /* val user = getInstance().user?.bonus?.forEach { bonus ->
            if (bonus.bonusType == BonusType.Chat) {
                nb += bonus.nombreItem
            }
        }*/

        return 0
    }

    private fun mockData(): UserModel {
        return UserModel(
            1,
            "Lm",
            "Lm@LM.LM",
            "\"https://cdn.artphotolimited.com/images/60df3a8fbd40b852ce5e0fff/300x300/big-smile-please.jpg",
            ClanBuilder.buildClan(1, "achamer"),
            10,
            150,
            50,
            10,
            /*mutableListOf(
                Bonus(BonusType.Soins, 3, "ico_health"),
                Bonus(BonusType.Croquette, 10, "ico_food"),
                Bonus(BonusType.Bouclier, 2, "ico_shield")
            )*/
        )
    }

    companion object {

        private var instance: ReposUser? = null

        fun getInstance(): ReposUser {
            if (instance == null) {
                instance = ReposUser()
                instance!!.user = instance!!.mockData()
            }
            return instance as ReposUser
        }
    }
}

