package be.technifuture.tff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NewUserModel(

    val login: String,
    val mail: String,
    val password: String,
    var clan: Int = 0,
    var urlAvatar: String = "",
) : Parcelable