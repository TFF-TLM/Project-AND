package be.technifuture.tff.utils.sharedPref

import android.content.Context
import android.content.SharedPreferences
import be.technifuture.tff.application.MyApp

class SharedPrefManager {
    companion object {
        private const val PREFERENCE_KEY = "be.technifuture.tff"
        val instance: SharedPrefManager by lazy {
            SharedPrefManager()
        }
    }

    enum class KeyPref(val value: String) {
        USER_ID("USER_ID"),
        REFRESH_TOKEN( "REFRESH_TOKEN"),
        EXPIRATION_TIME("EXPIRATION_TIME")
    }

    val sharedPref: SharedPreferences =
        MyApp.instance.getSharedPreferences(
            PREFERENCE_KEY,
            Context.MODE_PRIVATE
        )

    val editor: SharedPreferences.Editor = sharedPref.edit()
}