package be.technifuture.tff.service.network.dto

import be.technifuture.tff.model.PointInteret
import be.technifuture.tff.model.ZoneChat
import com.google.gson.annotations.SerializedName

data class SurroundingsResponse (
    @SerializedName("interest_points")
    val interestPoints: List<InterestPoint>,
    val cats: List<CatWithInteract>
){
    fun toZoneChatList(): List<ZoneChat> {
        val list = mutableListOf<ZoneChat>()
        cats.forEach {
            list.add(it.toZoneChat())
        }
        return list
    }

    fun toPointInteretList(): List<PointInteret> {
        val list = mutableListOf<PointInteret>()
        interestPoints.forEach {
            list.add(it.toPointInteret())
        }
        return list
    }
}