package be.technifuture.tff.model

import android.os.Parcel
import android.os.Parcelable

data class GpsCoordinates(val latitude: Double, val longitude: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<GpsCoordinates> {
            override fun createFromParcel(parcel: Parcel): GpsCoordinates {
                return GpsCoordinates(parcel)
            }

            override fun newArray(size: Int): Array<GpsCoordinates?> {
                return arrayOfNulls(size)
            }
        }
    }
}