package be.technifuture.tff.model
import android.os.Parcel
import android.os.Parcelable

class PointInteret(
    var id: String,
    var nom: String,
    var bonusLevel: Int,
    var bonusType: Int,
    var gpsCoordinates: GpsCoordinates
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(GpsCoordinates::class.java.classLoader) ?: GpsCoordinates(0.0, 0.0)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nom)
        parcel.writeInt(bonusLevel)
        parcel.writeInt(bonusType)
        parcel.writeParcelable(gpsCoordinates, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PointInteret> {
        override fun createFromParcel(parcel: Parcel): PointInteret {
            return PointInteret(parcel)
        }

        override fun newArray(size: Int): Array<PointInteret?> {
            return arrayOfNulls(size)
        }
    }
}
