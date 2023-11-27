package be.technifuture.tff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NewUserModel(

    val login: String,
    val mail: String,
    val password: String,
    val clan: Int,
    var urlAvatar: String,
) : Parcelable