package be.technifuture.tff.model

import com.google.gson.annotations.SerializedName
import java.net.URL

class UserModel(
    @SerializedName("user_id")
    val id: Int,
    val login: String,
    val mail: String,
    var urlAvatar: String,
    val clan: Int,
    val level: Int,
    var expMax: Int,
    var expActuel: Int,
    var nbCroquette: Int,
)

class UserModelAPI(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val login: String,
    @SerializedName("is_active")
    var isActive: Boolean,
    @SerializedName("data")
    var data: DataUser
)


class DataUser(
    @SerializedName("clan")
    var clan: ClanModel,
    @SerializedName("food")
    val food: Int,
    @SerializedName("limite_food")
    val limite: Int,
    @SerializedName("lvl")
    val level: Int,
    @SerializedName("exp")
    val experience: Int,
    @SerializedName("limite_exp")
    var expMax: Int,
    @SerializedName("image")
    var avatarUrl: URL,
)