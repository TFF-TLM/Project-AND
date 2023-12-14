package be.technifuture.tff.service.network.dto

import com.google.gson.annotations.SerializedName

data class SurroundingsResponse (
    @SerializedName("interest_points")
    val interestPoints: List<InterestPoint>,
    val cats: List<CatWithInteract>
)