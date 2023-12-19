package be.technifuture.tff.model

import android.graphics.Color
import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import be.technifuture.tff.service.network.dto.InteractCat
import be.technifuture.tff.service.network.dto.InteractCatWithUser
import be.technifuture.tff.utils.location.LocationManager

data class ZoneChat(
    var id: String,
    var nom: String,
    var radius: Int,
    var color: ChatRGB,
    var gpsCoordinates: GpsCoordinates?,
    var chat: Chat
) {
    fun toChat(): Chat {
        return chat
    }
}

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
    var isVisible: Boolean,
    var gpsCoordinates: GpsCoordinates?,
    var distanceFromUser: Int,
    var clan: ClanModel,
    var owner: String,
    var alive: Boolean,
    var foodreceived: Int,
    var interactFromUser: InteractCat? = null,
    var allInteract: List<InteractCatWithUser> = listOf()
) {
    fun updateDistance() {
        LocationManager.instance[LocationManager.KEY_LOCATION_MANAGER]?.localisationUser?.let { gpsUser ->
            gpsCoordinates?.let { gpsCat ->
                val results = FloatArray(1)
                Location.distanceBetween(
                    gpsUser.latitude,
                    gpsUser.longitude,
                    gpsCat.latitude,
                    gpsCat.longitude,
                    results
                )
                distanceFromUser = results[0].toInt()
            }
        }
    }
}

