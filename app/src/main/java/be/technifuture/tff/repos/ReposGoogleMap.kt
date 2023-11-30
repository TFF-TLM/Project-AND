package be.technifuture.tff.repos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import be.technifuture.tff.R
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.PointInteret
import be.technifuture.tff.model.ZoneChat
import be.technifuture.tff.model.interfaces.*
import be.technifuture.tff.model.mySetting
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


private fun GpsCoordinates.toLatLng(): LatLng {
    return LatLng(latitude.toDouble(), longitude.toDouble());
}


class ReposGoogleMap : OnMapReadyCallback {

    private var jeuxListenner: JeuxListener? = null

    private var itemsZoneChatShow: MutableList<ZoneChat> = mutableListOf()
    private var itemsPointInteretShow: MutableList<PointInteret> = mutableListOf()
    private lateinit var googleMap: GoogleMap
    private var zoomLevel: Float = 13f
    private lateinit var myContext: Context

    //val baseLongitude = 5.5314775
    //val baseLatitude = 50.6128178

    constructor( context: Context, zoom : Float, listenner : JeuxListener) {
        jeuxListenner = listenner
        myContext = context
        zoomLevel = zoom
        itemsZoneChatShow.addAll(ReposZoneChat.getInstance().mockData(5.5314775f, 50.6128178f))
        itemsPointInteretShow.addAll(ReposPointInteret.getInstance().mockData(5.5314775f, 50.6128178f))
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.clear()
        val greenIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        val defaultPosition = LatLng(mySetting.latitude, mySetting.longitude)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, zoomLevel))
        googleMap.addMarker(
            MarkerOptions()
                .position(defaultPosition)
                .title("Moi")
                .icon(greenIcon)
        )?.setTag("MOI")

        SetZoneChat()
        SetPointInteret()
        SetChat(myContext)

        googleMap.setOnMarkerClickListener { marker ->

            val pkGuid = marker.getTag().toString()
            if (pkGuid != "MOI" ) {


                var zoneChat: ZoneChat? = itemsZoneChatShow.find { it.chat.id == pkGuid }
                if (zoneChat != null) {
                    jeuxListenner?.onChatOpenned(zoneChat.chat)
                }

                var pointInteret: PointInteret? = itemsPointInteretShow.find { it.id == pkGuid }
                if (pointInteret != null) {
                    jeuxListenner?.onInterestOpenned(pointInteret)
                }

            }
            // Renvoyer false pour indiquer que le clic sur le marqueur doit également déclencher l'événement par défaut
            false
        }


    }

    private fun SetChat(context: Context) {
        itemsZoneChatShow.forEach { itemZoneChat: ZoneChat ->
            if (itemZoneChat.chat.gpsCoordinates.latitude != null && itemZoneChat.chat.gpsCoordinates.longitude != null) {
                val position = itemZoneChat.chat.gpsCoordinates.toLatLng()

                val originalBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ico_chat)
                val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 150, 150, false)
                val icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap)

                val markerOptions = MarkerOptions()
                    .zIndex(30f)
                    .position(position)
                    .icon(icon)
                    .anchor(0.5f, 0.5f)
                googleMap.addMarker(markerOptions)?.tag = itemZoneChat.chat.id
            }
        }
    }

    private fun SetPointInteret() {
        itemsPointInteretShow.forEach { itemPointInteret: PointInteret ->
            if (itemPointInteret.gpsCoordinates.latitude != null && itemPointInteret.gpsCoordinates.longitude != null) {
                val position = itemPointInteret.gpsCoordinates.toLatLng()

                val customIcon = BitmapDescriptorFactory.fromResource(R.drawable.ico_poi)
                val markerOptions = MarkerOptions()
                    .zIndex(20f)
                    .position(position)
                    .icon(customIcon)
                    .title(itemPointInteret.nom)

                googleMap.addMarker(markerOptions)?.tag = itemPointInteret.id

            }
        }
    }

    private fun SetZoneChat() {
        itemsZoneChatShow.forEach { itemZoneChat: ZoneChat ->
            if (itemZoneChat.gpsCoordinates.latitude != null && itemZoneChat.gpsCoordinates.longitude != null) {
                val position = itemZoneChat.gpsCoordinates.toLatLng()
                val radiusInMeters = itemZoneChat.radius.toDouble() // Utilisez la valeur de radius

                val circleOptions = CircleOptions()
                    .zIndex(10f)
                    .center(position)
                    .radius(radiusInMeters)
                    .strokeWidth(2f)
                    .strokeColor(Color.BLUE)
                    .fillColor(
                        Color.argb(
                            128,
                            itemZoneChat.color.r,
                            itemZoneChat.color.g,
                            itemZoneChat.color.b
                        )
                    )
                googleMap.addCircle(circleOptions).tag = itemZoneChat.id
            }
        }
    }

}