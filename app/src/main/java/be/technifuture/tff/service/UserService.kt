package be.technifuture.tff.service

import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel

class UserService {

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
            onCompletion(UserModel(
                1,"Tony", "user_test@tff.be",
                "12345678",
                1
            ))
        } else {
            onCompletion(null)
        }

    }

    fun getUserById(id: Int, onCompletion: (UserModel) -> Unit){
        onCompletion(UserModel(
            1,"Tony", "user_test@tff.be",
            "12345678",
            1
        ))

    }

    fun insertUser(user: NewUserModel) {
        //TODO: Insertien d'un user à l'API
    }

    fun setClanUser(user: NewUserModel) {
        //TODO: set le clan d'un user
    }

    fun setPosition(latittudu: Double, longittude: Double) {
        //TODO: inserer un location dans l'API
    }

    fun generateAvatar(job: String, animal: String, hobby: String) {
        //TODO: envoyer les promp et recevoir une Image
    }
}