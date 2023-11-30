package be.technifuture.tff.service

import be.technifuture.tff.model.Bonus
import be.technifuture.tff.model.BonusType
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel
import java.lang.Thread.sleep

class UserService {

    private val mockUser = UserModel(
        643,"Tony", "user_test@tff.be",
        "12345678",
        2, 5,530, 140,
        345,
        mutableListOf(
            Bonus(BonusType.Croquette, 10, "https://img1.freepng.fr/20180715/piy/kisspng-cat-chicken-as-food-elderly-crispy-fried-chicken-s-croquette-5b4b0a2245a2b8.5912826215316444502852.jpg"),
            Bonus(BonusType.Bouclier, 2, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBZG_ry2wJ5uNGBqeHpupjJ2uH4daVZtW29hqd4tSZLyhvPREQlfsClKY7irR7UEqlL-4&usqp=CAU")
        )

    )

    fun isLoginAvailable(login: String): Boolean {
        val tempUserYetUse = mutableListOf("tony", "medhi", "laurent", "user")

        return !tempUserYetUse.contains(login.lowercase())
    }

    fun isEmailAvailable(email: String): Boolean {
        val tempUserYetUse = mutableListOf("a@a.a", "a@b.c", "b@b.b", "b@b.c")
        return !tempUserYetUse.contains(email)
    }

    fun getUserByLogin(login: String, pass: String, onCompletion: (UserModel?) -> Unit) {
        //TODO: Appel pour verifier l'user
        val mockUserLogin = mutableListOf("tony", "medhi", "laurent", "user")
        val mockMdp = "12345678"

        return if (mockUserLogin.contains(login.lowercase()) &&
            mockMdp == pass
        ) {
            onCompletion(mockUser)
        } else {
            onCompletion(null)
        }

    }

    fun getUserById(id: Int, onCompletion: (UserModel) -> Unit){
        onCompletion(mockUser)

    }

    fun insertUser(user: NewUserModel, onComplet: (UserModel) -> Unit){
        onComplet(mockUser)
        //TODO: Insertien d'un user à l'API
    }

    fun generateAvatar(list: MutableList<String>, onComplet: (String) -> Unit) {
        sleep(3000)
        onComplet("url_de_api")
    }
}

object UserConnected{
    lateinit var user: UserModel
    lateinit var clan: ClanModel
}