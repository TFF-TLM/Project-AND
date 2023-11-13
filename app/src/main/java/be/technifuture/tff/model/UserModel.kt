package be.technifuture.tff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel(

    val login: String,
    val mail: String,
    val password: String,
    var urlAvatar: String,
) : Parcelable