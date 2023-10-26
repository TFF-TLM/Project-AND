package be.technifuture.tff.service

import android.util.Log
import be.technifuture.tff.model.UserModel

class UserService {
        fun isLoginAvailable(login: String): Boolean{
            // Must start by letter, Only Number and Letter for other
            // Max : 20 charactére
            val regex = "^(?=.*[A-Za-z0-9]\$)[A-Za-z][A-Za-z\\d.-]{0,19}\$".toRegex()

            // Login Match avec nos Régles ?
            if(regex.matches(login)){
                Log.d("TEST", "isLoginAvailable : Regex Ok")
            }else{
                Log.d("TEST", "isLoginAvailable : Regex NOk")
                return false
            }

            // Si oui existe t'il dans le BackEnd ?
            val tempUserYetUse = mutableListOf("tony","medhi","laurent","user")
            return !tempUserYetUse.contains(login.lowercase())

        }

        fun getUserByLogin(login: String, pass: String): Boolean{
            //TODO: Appel pour verifier l'user
            return true
        }

        fun insertUser(user: UserModel){
            //TODO: Insertien d'un user à l'API
        }

        fun setClanUser(user: UserModel){
            //TODO: set le clan d'un user
        }

        fun setPosition(latittudu: Double, longittude: Double){
            //TODO: inserer un location dans l'API
        }

        fun generateAvatar(job: String, animal: String, hobby: String){
            //TODO: envoyer les promp et recevoir une Image
        }
}