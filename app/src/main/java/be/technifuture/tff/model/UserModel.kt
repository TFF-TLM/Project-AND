package be.technifuture.tff.model

class UserModel(

    val login: String,
    val mail: String,
    val password: String,
    var gpsCoordinates : GpsCoordinates

)