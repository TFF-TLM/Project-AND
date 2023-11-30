package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.GpsCoordinates

interface LocationChangeListener {
    fun onLocationChanged(gpsCoordinatesUser: GpsCoordinates)
}