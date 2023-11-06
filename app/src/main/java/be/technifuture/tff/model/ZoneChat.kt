package be.technifuture.tff.model
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

data class ZoneChat(
    var id: String,
    var nom: String,
    var radius: Int,
    var color: ChatRGB,
    var gpsCoordinates: GpsCoordinates
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readParcelable(ChatRGB::class.java.classLoader) ?: ChatRGB(0, 0, 0),
        parcel.readParcelable(GpsCoordinates::class.java.classLoader) ?: GpsCoordinates(0.0, 0.0)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nom)
        parcel.writeInt(radius)
        parcel.writeParcelable(color, flags)
        parcel.writeParcelable(gpsCoordinates, flags)
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
