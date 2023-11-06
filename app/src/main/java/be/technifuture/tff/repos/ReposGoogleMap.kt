package be.technifuture.tff.repos

import android.graphics.Color
import be.technifuture.tff.R
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.PointInteret
import be.technifuture.tff.model.ZoneChat
import be.technifuture.tff.model.mySetting
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions


private fun GpsCoordinates.toLatLng(): LatLng {
    return LatLng(latitude.toDouble(), longitude.toDouble());
}


class ReposGoogleMap : OnMapReadyCallback {

    private var itemsZoneChatShow: MutableList<ZoneChat> = mutableListOf()
    private var itemsPointInteretShow: MutableList<PointInteret> = mutableListOf()
    private lateinit var googleMap: GoogleMap
    private var zoomLevel: Float = 13f


    //val baseLongitude = 5.5314775
    //val baseLatitude = 50.6128178

    constructor(zoom : Float) {
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

        googleMap.setOnInfoWindowClickListener { marker ->
            val pkGuid = marker.getTag().toString()
            if(pkGuid != "MOI") {
                var zoneChat: ZoneChat? = itemsZoneChatShow.find { it.id == pkGuid }
                // ajouter pour naviger vers le chat detail en envoyer chat

                var pointInteret: PointInteret? = itemsPointInteretShow.find { it.id == pkGuid }
                // ajouter pour naviger vers le chat detail en envoyer chat
            }
        }
    }

    private fun SetPointInteret() {
        itemsPointInteretShow.forEach { itemPointInteret: PointInteret ->
            if (itemPointInteret.gpsCoordinates.latitude != null && itemPointInteret.gpsCoordinates.longitude != null) {
                val position = itemPointInteret.gpsCoordinates.toLatLng()

                val customIcon = BitmapDescriptorFactory.fromResource(R.drawable.ico_poi)
                val markerOptions = MarkerOptions()
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