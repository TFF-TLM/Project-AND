package be.technifuture.tff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel(

    val id: Int,
    val login: String,
    val mail: String,
    var urlAvatar: String,
    val clan: Int

) : Parcelable