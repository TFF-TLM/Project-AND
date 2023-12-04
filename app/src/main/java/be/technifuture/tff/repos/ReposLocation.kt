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


    fun calculateDistance(gpsCoordinates1 : GpsCoordinates, gpsCoordinates2 : GpsCoordinates): Double {

        try{
            val earthRadius = 6371.0 // Rayon moyen de la Terre en kilomètres
            val dLat = degToRad(gpsCoordinates2.latitude - gpsCoordinates1.latitude)
            val dLon = degToRad(gpsCoordinates2.longitude - gpsCoordinates1.longitude)

            val a = Math.sin(dLat / 2).pow(2) +
                    Math.cos(degToRad(gpsCoordinates1.latitude)) * Math.cos(degToRad(gpsCoordinates2.latitude)) *
                    Math.sin(dLon / 2).pow(2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

            val distance = earthRadius * c
            return distance
        } catch (e: NumberFormatException){
            Log.d("Distance", e.toString())
            return 5000.0
        }
    }

    private fun degToRad(degrees: Double): Double {
        return degrees * (Math.PI / 180)
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

            Log.d("LM", "ReposLacolisation.getLastLocation")
            var gpsCoordinates: GpsCoordinates = GpsCoordinates(mySetting.latitude, mySetting.longitude );
            gpsUpadateListener?.onGpsChanged(gpsCoordinates)
        } // end Else
    }

    public fun onResumed(){
        if(locationManager != null) {
            locationManager!!.removeUpdates(getInstance())
        }
        locationManager=null
    }


    override fun onLocationChanged(location: Location) {
        mySetting.latitude = location.latitude
        mySetting.longitude = location.longitude
        Log.d("LM", "ReposLacolisation.onLocationChanged")
        var gpsCoordinates: GpsCoordinates = GpsCoordinates(mySetting.latitude, mySetting.longitude );
        gpsUpadateListener?.onGpsChanged(gpsCoordinates)

    }

}

