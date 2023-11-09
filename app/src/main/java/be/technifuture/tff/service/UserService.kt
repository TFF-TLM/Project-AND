package be.technifuture.tff.service

import android.util.Log
import be.technifuture.tff.model.UserModel
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class UserService {

    private val algorithme = "PBKDF2WithHmacSHA512"
    private val iteration = 120_000
    private val keyLenght = 256
    private val secret = "#C@Ti$-Kut"


        fun isLoginAvailable(login: String): Boolean {
            val tempUserYetUse = mutableListOf("tony","medhi","laurent","user")

            return !tempUserYetUse.contains(login.lowercase())
        }

    fun isEmailAvailable(email: String): Boolean{
        val tempUserYetUse = mutableListOf("a@a.a","a@b.c","b@b.b","b@b.c")
        return !tempUserYetUse.contains(email)
    }

        fun getUserByLogin(login: String, pass: String): Boolean{
            //TODO: Appel pour verifier l'user
            val mockUserLogin = mutableListOf("tony","medhi","laurent","user")
            // Hash de "12345678"
            val mockMdp = "a510d00003ea6b92c238dd728b2048209c78f51de5f62ac0c8db07e87397b4d4"


            return mockUserLogin.contains(login.lowercase()) &&
                    mockMdp == generateHash(pass)
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
    @OptIn(ExperimentalStdlibApi::class)
    private fun generateHash(password: String): String {
        val combinedSalt = secret.toByteArray()
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(algorithme)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), combinedSalt, iteration, keyLenght)
        val key: SecretKey = factory.generateSecret(spec)
        val hash: ByteArray = key.encoded
        return hash.toHexString()
    }
}