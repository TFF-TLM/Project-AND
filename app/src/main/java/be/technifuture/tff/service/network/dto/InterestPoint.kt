package be.technifuture.tff.service.network.dto

data class InterestPoint (
    val id: Int,
    val latitude: Float,
    val longitude: Float,
    val interact: List<Interact>
)