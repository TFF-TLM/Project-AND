package be.technifuture.tff.utils.location

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Looper
import android.util.Log
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.utils.permission.PermissionsCheckerManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY

class LocationManager(
    activity: Activity,
    private var locationCallback: LocationCallback?,
    private val requestCode: Int,
    instanceKey: String
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val permissionsChecker = PermissionsCheckerManager(activity)
    var localisationUser : GpsCoordinates? = null

    companion object {
        val instance: MutableMap<String, LocationManager> = mutableMapOf()
        const val KEY_LOCATION_MANAGER = "JeuxActivityLocation"
        const val LOCATION_PERMISSION_REQUEST_CODE = 111

        fun removeInstance(key: String){
            instance.remove(key)
        }
    }

    init {
        if (instance[instanceKey] == null) {
            instance[instanceKey] = this
            Log.d("LM", "init")
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        permissionsChecker.checkPermissionsLocation(requestCode) {
            locationCallback?.let {
                fusedLocationClient.requestLocationUpdates(
                    getRequestForFusedLocation(),
                    it,
                    Looper.getMainLooper()
                )
            }
        }
    }

    fun removeUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    fun setUserCoordinates(lat: Double, lon: Double){
        localisationUser = GpsCoordinates(lat, lon)
    }

    fun setCallback(handler: (Location) -> Unit){
        Log.d("LM", "set callback")
        locationCallback = object : LocationCallback() {
            @SuppressLint("MissingPermission")
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    //it?.removeUpdates()
                    setUserCoordinates(location.latitude, location.longitude)
                    handler(location)
                }
            }
        }
    }

    private fun getRequestForFusedLocation(): LocationRequest {
        return LocationRequest.Builder(1000)
            .setMinUpdateDistanceMeters(1f)
            .setPriority(PRIORITY_HIGH_ACCURACY)
            .build()
    }

}