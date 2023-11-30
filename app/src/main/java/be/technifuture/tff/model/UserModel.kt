package be.technifuture.tff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel(

    val id: Int,
    val login: String,
    val mail: String,
    var urlAvatar: String,
    val clan: Int,
    val level: Int,
    var expMax: Int,
    var expActuel: Int,
    var nbCroquette: Int

) : Parcelable