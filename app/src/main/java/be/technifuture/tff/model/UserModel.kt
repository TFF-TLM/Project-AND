package be.technifuture.tff.model

import com.google.gson.annotations.SerializedName
import java.net.URL

class UserModel(
    val id: Int,
    val login: String,
    val mail: String,
    var urlAvatar: String,
    val clan: ClanModel,
    val level: Int,
    var expMax: Int,
    var expActuel: Int,
    var nbCroquette: Int,
    var croquetteMax: Int = 0
)