package be.technifuture.tff.repos

import be.technifuture.tff.model.Bonus
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.model.enums.BonusType

class ReposUser {
    fun mockData(): UserModel {
        var userModel : UserModel = UserModel(
            1,
            "Lm",
            "Lm@LM.LM",
            "\"https://cdn.artphotolimited.com/images/60df3a8fbd40b852ce5e0fff/300x300/big-smile-please.jpg",
            1,
            10,
            150,
        )
        return userModel
    }

    companion object {

        private var instance: ReposUser? = null

        fun getInstance(): ReposUser {
            if (instance == null) {
                instance = ReposUser()
            }
            return instance as ReposUser
        }
    }
}

