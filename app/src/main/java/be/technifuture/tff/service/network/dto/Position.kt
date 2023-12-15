package be.technifuture.tff.service.network.dto

import be.technifuture.tff.model.GpsCoordinates

data class Position(
    val id: Int,
    val latitude: Float,
    val longitude: Float
) {
    fun toGpsCoordinates(): GpsCoordinates {
        return GpsCoordinates(this.latitude.toDouble(), this.longitude.toDouble())
    }
}