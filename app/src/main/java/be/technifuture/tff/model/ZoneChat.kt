package be.technifuture.tff.model
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

data class ZoneChat(
    var id: String,
    var nom: String,
    var radius: Int,
    var color: ChatRGB,
    var gpsCoordinates: GpsCoordinates,
    var chat: Chat
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readParcelable(ChatRGB::class.java.classLoader) ?: ChatRGB(0, 0, 0),
        parcel.readParcelable(GpsCoordinates::class.java.classLoader) ?: GpsCoordinates(0.0, 0.0),
        parcel.readParcelable(Chat::class.java.classLoader) ?: Chat("", "", "", 0, 100, 1, false, GpsCoordinates(0.0, 0.0),0)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nom)
        parcel.writeInt(radius)
        parcel.writeParcelable(color, flags)
        parcel.writeParcelable(gpsCoordinates, flags)
        parcel.writeParcelable(chat, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ZoneChat> {
        override fun createFromParcel(parcel: Parcel): ZoneChat {
            return ZoneChat(parcel)
        }

        override fun newArray(size: Int): Array<ZoneChat?> {
            return arrayOfNulls(size)
        }
    }
}

data class ChatRGB(val r: Int, val g: Int, val b: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(r)
        parcel.writeInt(g)
        parcel.writeInt(b)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<ChatRGB> {
            override fun createFromParcel(parcel: Parcel): ChatRGB {
                return ChatRGB(parcel)
            }

            override fun newArray(size: Int): Array<ChatRGB?> {
                return arrayOfNulls(size)
            }
        }
    }
}


data class Chat(
    var id: String,
    var urlImage: String,
    var nom: String,
    var vie: Int,
    var maxVie: Int,
    var level: Int,
    var isVisible : Boolean,
    var gpsCoordinates: GpsCoordinates,
    var distanceFromUser: Int,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(GpsCoordinates::class.java.classLoader) ?: GpsCoordinates(0.0, 0.0),
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(urlImage)
        parcel.writeString(nom)
        parcel.writeInt(vie)
        parcel.writeInt(maxVie)
        parcel.writeInt(level)
        parcel.writeByte(if (isVisible) 1 else 0)
        parcel.writeParcelable(gpsCoordinates, flags)
        parcel.writeInt(distanceFromUser)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}

