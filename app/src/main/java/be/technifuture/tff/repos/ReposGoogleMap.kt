package be.technifuture.tff.repos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import be.technifuture.tff.R
import be.technifuture.tff.application.MyApp
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.PointInteret
import be.technifuture.tff.model.ZoneChat
import be.technifuture.tff.model.enums.ColorChoice
import be.technifuture.tff.model.interfaces.*
import be.technifuture.tff.model.mySetting
import be.technifuture.tff.service.network.manager.GameDataManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil

fun GpsCoordinates.toLatLng(): LatLng {
    return LatLng(latitude.toDouble(), longitude.toDouble());
}

class ReposGoogleMap : OnMapReadyCallback {

    companion object {
        private var instance: ReposGoogleMap? = null
        fun getInstance(): ReposGoogleMap {
            if (instance == null) {
                instance = ReposGoogleMap()
            }
            return instance as ReposGoogleMap
        }
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var greenIcon: BitmapDescriptor
    private lateinit var yellowIcon: BitmapDescriptor

    private var myMarker: Marker? = null
    private var isMapLoaded: Boolean = false
    private var jeuxListenner: JeuxListener? = null
    private var markerList = mutableListOf<Marker>()

    //private var itemsZoneChatShow: MutableList<ZoneChat> = mutableListOf()
    //private var itemsPointInteretShow: MutableList<PointInteret> = mutableListOf()
    private var zoomLevel: Float = 16f

    //val baseLongitude = 5.5314775
    //val baseLatitude = 50.6128178

    public fun Init(zoom: Float, listenner: JeuxListener) {
        jeuxListenner = listenner
        zoomLevel = zoom
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.clear()
        greenIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        yellowIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)

        googleMap.uiSettings.isRotateGesturesEnabled = false

        googleMap.uiSettings.isScrollGesturesEnabled = false
        googleMap.setMinZoomPreference(14f)
        googleMap.setMaxZoomPreference(20f)
        googleMap.moveCamera(
            CameraUpdateFactory.zoomTo(zoomLevel)
        )

        this.isMapLoaded = true
    }


    public fun CalculateNewPosition(
        oldPosition: GpsCoordinates,
        angle: Double,
        speed: Double
    ): GpsCoordinates {
        val angleInRadians = Math.toRadians(angle)
        val newLatitude = oldPosition.latitude + speed * -Math.sin(angleInRadians)
        val newLongitude = oldPosition.longitude + speed * Math.cos(angleInRadians)
        return GpsCoordinates(newLatitude, newLongitude)
    }


    public fun SetPosition(gpsCoordinatesUser: GpsCoordinates, color: ColorChoice) {

        if (this.isMapLoaded) {
            if (myMarker == null) {
                myMarker = googleMap.addMarker(
                    MarkerOptions()
                        .position(gpsCoordinatesUser.toLatLng())
                        .title("Moi")
                        .icon(greenIcon)
                )
                myMarker?.setTag("MOI")
            }

            if (color == ColorChoice.Green) {
                myMarker?.setIcon(greenIcon)
            }
            if (color == ColorChoice.Yellow) {
                myMarker?.setIcon(yellowIcon)
            }
            myMarker?.position = gpsCoordinatesUser.toLatLng()
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(gpsCoordinatesUser.toLatLng()))
        }
    }

    private fun SetChat(cats: List<ZoneChat>) {
        cats.forEach { itemZoneChat ->
            if (itemZoneChat.chat.alive) {
                itemZoneChat.gpsCoordinates?.let { pos ->
                    val position = pos.toLatLng()

                    val originalBitmap =
                        BitmapFactory.decodeResource(MyApp.instance.resources, R.drawable.ico_chat)
                    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 150, 150, false)
                    val icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap)

                    val markerOptions = MarkerOptions()
                        .zIndex(30f)
                        .position(position)
                        .icon(icon)
                        .anchor(0.5f, 0.5f)
                    val marker = googleMap.addMarker(markerOptions)
                    marker?.tag = "CH" + itemZoneChat.chat.id
                    marker?.let{
                        markerList.add(it)
                    }
                }
            }
        }
    }

    private fun SetPointInteret(points: List<PointInteret>) {
        points.forEach { itemPointInteret ->
            val position = itemPointInteret.gpsCoordinates.toLatLng()
            val customIcon = BitmapDescriptorFactory.fromResource(R.drawable.ico_poi)
            val markerOptions = MarkerOptions()
                .zIndex(20f)
                .position(position)
                .icon(customIcon)
                .title(" ")

            googleMap.addMarker(markerOptions)?.tag = "PI" + itemPointInteret.id
        }
    }

    private fun SetZoneChat(cats: List<ZoneChat>) {
        cats.forEach { itemZoneChat: ZoneChat ->
            if (itemZoneChat.chat.alive) {
                itemZoneChat.gpsCoordinates?.let { pos ->
                    val position = pos.toLatLng()
                    val newLatLng =
                        SphericalUtil.computeOffset(position, itemZoneChat.radius.toDouble(), 0.0)
                    val radiusInMeters: Double =
                        SphericalUtil.computeDistanceBetween(position, newLatLng)
                    Log.d(
                        "Lm",
                        " radius = " + itemZoneChat.radius.toString() + " distance = " + radiusInMeters.toString()
                    )

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
                    googleMap.addCircle(circleOptions).tag = "ZC" + itemZoneChat.id
                }
            }
        }
    }

    private fun setListenerMarker(cats: List<ZoneChat>, interestPoints: List<PointInteret>) {
        googleMap.setOnMarkerClickListener { marker ->
            val pkGuid = marker.getTag().toString()
            if (pkGuid != "MOI") {

                val cat = cats.find { "CH" + it.chat.id == pkGuid }
                if (cat != null) {
                    jeuxListenner?.onChatOpenned(cat.id)
                }

                val pointInteret = interestPoints.find { "PI" + it.id == pkGuid }
                if (pointInteret != null) {
                    jeuxListenner?.onInterestOpenned(pointInteret.id)
                }

            }
            // Renvoyer false pour indiquer que le clic sur le marqueur doit également déclencher l'événement par défaut
            false
        }
    }

    private fun removeAllExceptSelf(){
        markerList.forEach { it.remove() }
        markerList.clear()
    }
    fun updateCatsAndPoints(lat: Float, lon: Float) {
        GameDataManager.instance.getSurroundings(lat, lon) { cats, points, _ ->
            if (cats != null && points != null) {
                removeAllExceptSelf()
                SetZoneChat(cats)
                SetPointInteret(points)
                SetChat(cats)
                setListenerMarker(cats, points)
            }
        }
    }
}