package be.technifuture.tff.service.network.dto

import com.google.gson.annotations.SerializedName

data class Interact(
    val id: Int,
    val timestamp: String
)

data class InteractCat(
    val id: Int,
    val timestamp: String,
    @SerializedName("given_food")
    val givenFood: Int
)

data class InteractCatWithUser(
    val id: Int,
    val timestamp: String,
    @SerializedName("given_food")
    val givenFood: Int,
    val user: UserInfoResponse
) {
    fun toInteractCat(): InteractCat {
        return InteractCat(id, timestamp, givenFood)
    }
}

data class InteractCatResponse(
    @SerializedName("user_data")
    val userData: UserDataResponse,
    val cat: Cat,
    val interact: InteractCat
)

data class InteractInterestPointResponse(
    @SerializedName("user_data")
    val userData: UserDataResponse,
    val cat: Cat?,
    @SerializedName("gained_food")
    val foodGain: Int
)
