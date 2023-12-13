package be.technifuture.tff.model

import com.google.gson.annotations.SerializedName
import java.net.URL


class UserModel(
    @SerializedName("user_id")
    val id: Int,
    @SerializedName("user_login")
    val login: String,
    @SerializedName("user_mail")
    val mail: String,
    @SerializedName("user_url")
    var urlAvatar: String,
    @SerializedName("user_clan")
    val clan: Int,
    @SerializedName("user_exp")
    val level: Int,
    @SerializedName("user_croquette")
    var nbCroquette: Int,

    /*@SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val login: String,
    @SerializedName("is_active")
    var isActive: Boolean,
    @SerializedName("data")
    var data: DataUser*/

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