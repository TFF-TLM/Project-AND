package be.technifuture.tff.service

import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel
import java.lang.Thread.sleep

class UserService {

    private val mockUser = UserModel(
        1,"Tony", "user_test@tff.be",
        "12345678",
        1
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
}