package be.technifuture.tff.model
import android.os.Parcel
import android.os.Parcelable

data class Chat(
    var id: String,
    var urlImage: String,
    var nom: String,
    var vie: Int,
    var level: Int,
    var gpsCoordinates: GpsCoordinates
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(GpsCoordinates::class.java.classLoader) ?: GpsCoordinates(0.0, 0.0)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(urlImage)
        parcel.writeString(nom)
        parcel.writeInt(vie)
        parcel.writeInt(level)
        parcel.writeParcelable(gpsCoordinates, flags)
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
