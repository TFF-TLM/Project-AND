package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.GpsCoordinates

interface GpsUpadateListener {
    fun onGpsChanged(gpsCoordinatesUser: GpsCoordinates)
}