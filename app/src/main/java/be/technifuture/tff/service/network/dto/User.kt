package be.technifuture.tff.service.network.dto

import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.network.utils.ClanBuilder
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
) {
    fun toUserModel(): UserModel? {
        var user: UserModel? = null
        this.data?.let { userData ->
            user = UserModel(
                this.id,
                this.username,
                "",
                userData.image,
                userData.clan.toClanModel(),
                userData.lvl,
                userData.expLimit,
                userData.exp,
                userData.food,
                userData.foodLimit
            )
        }
        return user
    }
}

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
){
    fun updateUserModel(user: UserModel): UserModel {
        user.urlAvatar = this.image
        user.clan = this.clan.toClanModel()
        user.level = this.lvl
        user.expMax = this.expLimit
        user.expActuel = this.exp
        user.nbCroquette = this.food
        user.croquetteMax = this.foodLimit
        return user
    }
}

data class UserDataRequestBody(
    @SerializedName("clan_id")
    val clan: Int,
    val animal: String,
    val landscape: String,
    val hobby: String
)