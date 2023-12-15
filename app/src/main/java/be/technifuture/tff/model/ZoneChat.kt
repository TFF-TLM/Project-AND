package be.technifuture.tff.model
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

data class ZoneChat(
    var id: String,
    var nom: String,
    var radius: Int,
    var color: ChatRGB,
    var gpsCoordinates: GpsCoordinates?,
    var chat: Chat
)

data class ChatRGB(
    val r: Int,
    val g: Int,
    val b: Int
)


data class Chat(
    var id: String,
    var urlImage: String,
    var nom: String,
    var vie: Int,
    var maxVie: Int,
    var level: Int,
    var isVisible : Boolean,
    var gpsCoordinates: GpsCoordinates?,
    var distanceFromUser: Int,
    var clan: ClanModel,
    var owner: String,
    var alive: Boolean,
    var foodreceived: Int
)

