package be.technifuture.tff.repos

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import be.technifuture.tff.model.*
import be.technifuture.tff.model.interfaces.*
import kotlin.math.pow



class ReposLacolisation : LocationListener {
    private var gpsUpadateListener: GpsUpadateListener? = null
    private var locationManager : LocationManager? = null

    companion object {

        const val LOCATION_PERMISSION_REQUEST_CODE = 123
        const val MY_SHARED_PREFERENCE = "ActiCityPreference"
        const val FIRST_TIME_OPENING = "ActiCityOpen"
        private var instance: ReposLacolisation? = null

        fun getInstance(): ReposLacolisation {
            if (instance == null) {
                instance = ReposLacolisation()
            }
            return instance as ReposLacolisation
        }
    }

    fun setListenner(Listener : GpsUpadateListener){
        gpsUpadateListener = Listener
    }

    fun getLastLocation(activity : Activity, context : Context) {

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (mySetting.isFirstLaunch || ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )) {

                if(mySetting.isFirstLaunch == true){
                    var sharedPreference = context.getSharedPreferences(
                        MY_SHARED_PREFERENCE,
                        AppCompatActivity.MODE_PRIVATE
                    )
                    val editor = sharedPreference.edit()
                    editor.putBoolean(FIRST_TIME_OPENING, false)
                    editor.apply()
                }

                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {

                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1f,  getInstance())

            val lastKnownLocation =
                locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (lastKnownLocation != null) {
                val gpsCoordinates = GpsCoordinates(lastKnownLocation.latitude, lastKnownLocation.longitude)
                gpsUpadateListener?.onGpsChanged(gpsCoordinates)
            }
        } // end Else
    }

    public fun onResumed(){
        if(locationManager != null) {
            locationManager!!.removeUpdates(getInstance())
        }
        locationManager=null
    }


    override fun onLocationChanged(location: Location) {
        Log.d("LM", "ReposLacolisation.onLocationChanged")
        mySetting.LocalisationGps = GpsCoordinates(location.latitude,location.longitude)
        var gpsCoordinates = mySetting.LocalisationGps
        gpsUpadateListener?.onGpsChanged(gpsCoordinates)
    }

}

