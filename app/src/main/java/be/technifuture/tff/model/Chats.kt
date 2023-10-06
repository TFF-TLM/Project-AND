package be.technifuture.tff.model
class Chat(
    var id: String,
    var urlImage: String,
    var mom: String,
    var vie: Int,
    var level : Int,
    var gpsCoordinates : GpsCoordinates
)

data class GpsCoordinates(val latitude: Double, val longitude: Double)