package be.technifuture.tff.repos

import be.technifuture.tff.model.Bonus
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.model.enums.BonusType

class ReposUser {
    private var user: UserModel? = null


    fun getUser() : UserModel{
        return getInstance().user!!
    }

    fun getChatNb() : Int {
        var nb: Int = 0
       /* val user = getInstance().user?.bonus?.forEach { bonus ->
            if (bonus.bonusType == BonusType.Chat) {
                nb += bonus.nombreItem
            }
        }*/

        return nb
    }

    private fun mockData(): UserModel {
        var userModel : UserModel = UserModel(
            1,
            "Lm",
            "Lm@LM.LM",
            "\"https://cdn.artphotolimited.com/images/60df3a8fbd40b852ce5e0fff/300x300/big-smile-please.jpg",
            1,
            10,
            150,
            50,
            10,
            /*mutableListOf(
                Bonus(BonusType.Croquette, 3, "https://img1.freepng.fr/20180715/piy/kisspng-cat-chicken-as-food-elderly-crispy-fried-chicken-s-croquette-5b4b0a2245a2b8.5912826215316444502852.jpg"),
                Bonus(BonusType.Croquette, 10, "https://img1.freepng.fr/20180715/piy/kisspng-cat-chicken-as-food-elderly-crispy-fried-chicken-s-croquette-5b4b0a2245a2b8.5912826215316444502852.jpg"),
                Bonus(BonusType.Bouclier, 2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBZG_ry2wJ5uNGBqeHpupjJ2uH4daVZtW29hqd4tSZLyhvPREQlfsClKY7irR7UEqlL-4&usqp=CAU")
            )*/
        )
        return userModel
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

