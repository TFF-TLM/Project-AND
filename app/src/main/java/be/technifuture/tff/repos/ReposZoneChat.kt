package be.technifuture.tff.repos

import be.technifuture.tff.model.GpsCoordinates
import com.google.maps.android.SphericalUtil

class ReposZoneChat  {

    fun getDistance(source:GpsCoordinates, destination:GpsCoordinates): Double{
        val distance: Double = SphericalUtil.computeDistanceBetween(
            source!!.toLatLng(), destination.toLatLng()
        )
        return distance
    }

    companion object {

        private var instance: ReposZoneChat? = null

        fun getInstance(): ReposZoneChat {
            if (instance == null) {
                instance = ReposZoneChat()
            }
            return instance as ReposZoneChat
        }
    }
}