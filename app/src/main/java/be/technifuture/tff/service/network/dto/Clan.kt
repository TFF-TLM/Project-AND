package be.technifuture.tff.service.network.dto

import be.technifuture.tff.model.ChatRGB
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

    fun toChatRGB(): ChatRGB {
        return when (this.id) {
            1 -> ChatRGB(255, 255, 0)
            2 -> ChatRGB(193, 0, 0)
            3 -> ChatRGB(0, 153, 0)
            else -> ChatRGB(0, 0, 0)
        }
    }
}