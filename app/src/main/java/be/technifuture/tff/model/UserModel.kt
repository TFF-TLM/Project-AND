package be.technifuture.tff.model

import com.google.gson.annotations.SerializedName
import java.net.URL

class UserModel(
    var id: Int,
    var login: String,
    var mail: String,
    var urlAvatar: String,
    var clan: ClanModel,
    var level: Int,
    var expMax: Int,
    var expActuel: Int,
    var nbCroquette: Int,
    var croquetteMax: Int = 0
)