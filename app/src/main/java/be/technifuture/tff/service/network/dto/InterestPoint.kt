package be.technifuture.tff.service.network.dto

import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.PointInteret

data class InterestPoint(
    val id: Int,
    val latitude: Float,
    val longitude: Float,
    val interact: List<Interact>
) {
    fun toPointInteret(): PointInteret {
        return PointInteret(
            this.id.toString(),
            this.interact.isEmpty(),
            GpsCoordinates(this.latitude.toDouble(), this.longitude.toDouble())
        )
    }
}