package be.technifuture.tff.service.network.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("is_active")
    val isActive: Boolean
)

data class UserInfoResponse(
    val id: Int,
    val username: String,
    @SerializedName("is_active")
    val isActive: Boolean
)

data class UserDetailsResponse(
    val id: Int,
    val username: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    val data: UserDataResponse?
)

data class UserDataResponse(
    val clan: ClanResponse,
    val food: Int,
    @SerializedName("limite_food")
    val foodLimit: Int,
    val lvl: Int,
    val exp: Int,
    @SerializedName("limite_exp")
    val expLimit: Int,
    val image: String
)

data class UserDataRequestBody(
    @SerializedName("clan_id")
    val clan: Int,
    val animal: String,
    val landscape: String,
    val hobby: String
)