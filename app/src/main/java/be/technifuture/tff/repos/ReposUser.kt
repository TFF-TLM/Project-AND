package be.technifuture.tff.repos

import be.technifuture.tff.model.Bonus
import be.technifuture.tff.model.BonusType
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.UserModel
import kotlin.random.Random

class ReposUser {

    fun mockData(): UserModel {
        var userModel : UserModel = UserModel(
            "LM",
            "Lm@LM.LM",
            "LM",
            "https://cdn.artphotolimited.com/images/60df3a8fbd40b852ce5e0fff/300x300/big-smile-please.jpg",
            ClanModel(""),
            mutableListOf(
                Bonus(BonusType.Croquette, 10, "https://img1.freepng.fr/20180715/piy/kisspng-cat-chicken-as-food-elderly-crispy-fried-chicken-s-croquette-5b4b0a2245a2b8.5912826215316444502852.jpg"),
                Bonus(BonusType.Bouclier, 2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBZG_ry2wJ5uNGBqeHpupjJ2uH4daVZtW29hqd4tSZLyhvPREQlfsClKY7irR7UEqlL-4&usqp=CAU")
            )
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

