package be.technifuture.tff.model

class UserModel(

    val login: String,
    val mail: String,
    val password: String,
    val urlAvatar: String,
    val clan: ClanModel,
    var bonus: Bonus
)