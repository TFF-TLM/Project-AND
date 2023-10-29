package be.technifuture.tff

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import be.technifuture.tff.databinding.ActivityConnectBinding
import be.technifuture.tff.model.mySetting
import be.technifuture.tff.repos.ReposLacolisation

class ConnectActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreferences
    lateinit var binding: ActivityConnectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = applicationContext.getSharedPreferences(ReposLacolisation.MY_SHARED_PREFERENCE, MODE_PRIVATE)
        mySetting.isFirstLaunch = sharedPreference.getBoolean(ReposLacolisation.FIRST_TIME_OPENING, true)

        // Obtention de la dernière position de l'appareil
        ReposLacolisation.getInstance().getLastLocation(this, applicationContext)
    }
}
