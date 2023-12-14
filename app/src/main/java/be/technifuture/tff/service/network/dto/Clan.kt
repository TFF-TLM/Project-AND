package be.technifuture.tff.service.network.dto

import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.service.network.utils.ClanBuilder
import com.google.gson.annotations.SerializedName

data class ClanResponse(
    val id: Int,
    val name: String,
    @SerializedName("effect_id")
    val effectId: Int
) {
    fun toClanModel(): ClanModel {
        return ClanBuilder.buildClan(this.id, this.name)
    }
}