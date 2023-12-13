package be.technifuture.tff.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class UserModel(

    val id: Int,
    val login: String,
    val mail: String,
    var urlAvatar: String,
    val clan: Int,
    val level: Int,
    var expMax: Int,
    var expActuel: Int,
    var nbCroquette: Int,
    var bonus : MutableList<Bonus>
)