package be.technifuture.tff.service

import be.technifuture.tff.model.UserModel

class UserService {
    companion object{
        val userService = UserService

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
}