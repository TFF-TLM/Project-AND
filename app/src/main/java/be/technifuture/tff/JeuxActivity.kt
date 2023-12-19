package be.technifuture.tff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import be.technifuture.tff.databinding.ActivityJeuxBinding
import be.technifuture.tff.utils.location.LocationManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.pm.PackageManager.PERMISSION_GRANTED

class JeuxActivity : AppCompatActivity() {

    lateinit var binding: ActivityJeuxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJeuxBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitNav();
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationManager.removeInstance(LocationManager.KEY_LOCATION_MANAGER)
    }

    //*********************************************************************** Navigation
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp()
    }

    private fun InitNav() {
        val navView: BottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.loginFragment, R.id.radarFragment, R.id.jeuxFragment)
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, argumetn ->
            when (destination.id) {
                R.id.radarFragment -> {
                    toolbar.isVisible = false; toolbar.title = "Radar"; navView.isVisible = true;
                }

                R.id.jeuxFragment -> {
                    toolbar.isVisible = false; toolbar.title = "Jeux"; navView.isVisible = false;
                }

                R.id.loginFragment -> {
                    toolbar.isVisible = false; toolbar.title = ""; navView.isVisible = false;
                }

                else -> toolbar.title = null
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.first() == PERMISSION_GRANTED) {
            when (requestCode) {
                LocationManager.LOCATION_PERMISSION_REQUEST_CODE -> LocationManager.instance[LocationManager.KEY_LOCATION_MANAGER]?.getLastKnownLocation()
                else -> return
            }
        }
    }
}