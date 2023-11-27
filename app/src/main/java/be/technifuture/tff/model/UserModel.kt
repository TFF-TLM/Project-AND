package be.technifuture.tff.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//TODO: Type Clan, Int? ou ClanModel?
@Parcelize
class UserModel(

    val id: Int,
    val login: String,
    val mail: String,
    var urlAvatar: String,
    val clan: Int

) : Parcelable