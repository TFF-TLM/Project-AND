package be.technifuture.tff.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


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
)