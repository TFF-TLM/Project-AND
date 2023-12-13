package be.technifuture.tff.service.network.dto

import com.google.gson.annotations.SerializedName

data class ClanResponse(
    val id: Int,
    val name: String,
    @SerializedName("effect_id")
    val effectId: Int
)