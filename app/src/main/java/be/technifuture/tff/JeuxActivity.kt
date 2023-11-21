package be.technifuture.tff

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import be.technifuture.tff.databinding.ActivityJeuxBinding
import be.technifuture.tff.model.mySetting
import be.technifuture.tff.repos.ReposGoogleMap
import be.technifuture.tff.repos.ReposLacolisation
import com.google.android.material.bottomnavigation.BottomNavigationView

class JeuxActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreferences
    lateinit var binding: ActivityJeuxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeuxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = applicationContext.getSharedPreferences(ReposLacolisation.MY_SHARED_PREFERENCE, MODE_PRIVATE)
        mySetting.isFirstLaunch = sharedPreference.getBoolean(ReposLacolisation.FIRST_TIME_OPENING, true)

        // Obtention de la dernière position de l'appareil
        ReposLacolisation.getInstance().getLastLocation(this, applicationContext)

        InitNav();


    }

    //*********************************************************************** Navigation
    override fun onSupportNavigateUp() : Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return  navController.navigateUp()
    }

    private fun InitNav(){
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.loginFragment, R.id.radarFragment, R.id.jeuxFragment)
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{controller, destination, argumetn ->
            when(destination.id){
                R.id.radarFragment -> {toolbar.isVisible = false; toolbar.title = "Radar"; navView.isVisible = true;}
                R.id.jeuxFragment ->  {toolbar.isVisible = false; toolbar.title = "Jeux"; navView.isVisible = true;}
                R.id.loginFragment -> {toolbar.isVisible = false; toolbar.title = ""; navView.isVisible = false;}
                else -> toolbar.title = null
            }
        }
    }
}