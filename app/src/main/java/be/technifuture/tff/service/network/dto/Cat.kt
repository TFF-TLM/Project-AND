package be.technifuture.tff.service.network.dto

import com.google.gson.annotations.SerializedName

data class CatWithInteract (
    val id: Int,
    val owner: UserInfoResponse,
    val clan: ClanResponse,
    val name: String,
    val lvl: Int,
    val exp: Int,
    @SerializedName("limite_exp")
    val limiteExp: Int,
    val timestamp: String,
    val image: String,
    val origin: Position,
    val position: Position,
    val alive: Boolean,
    val radius: Int,
    val interact: List<InteractCat>
)

data class Cat (
    val id: Int,
    val owner: UserInfoResponse,
    val clan: ClanResponse,
    val name: String,
    val lvl: Int,
    val exp: Int,
    @SerializedName("limite_exp")
    val limiteExp: Int,
    val timestamp: String,
    val image: String,
    val origin: Position,
    val position: Position,
    val alive: Boolean,
    val radius: Int
)

data class CatInBagResponse(
    val cats: List<Cat>
)

data class CatOnMapResponse(
    val cats: List<CatWithInteract>
)

data class DropCatRequestBody(
    @SerializedName("cat_id")
    val catId: Int,
    val name: String,
    val latitude: Float,
    val longitude: Float
)
data class DropCatResponse(
    val cat: Cat
)
