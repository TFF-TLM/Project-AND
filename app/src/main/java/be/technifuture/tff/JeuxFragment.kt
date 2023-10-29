package be.technifuture.tff

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.databinding.FragmentJeuxBinding
import be.technifuture.tff.databinding.FragmentRadarBinding
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.mySetting
import be.technifuture.tff.repos.ReposChat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class JeuxFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentJeuxBinding

    private var itemsChatShow: MutableList<Chat> = mutableListOf()
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJeuxBinding.inflate(layoutInflater)

        itemsChatShow.addAll(ReposChat.getInstance().mockData())

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.clear()
        val greenIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        val defaultPosition = LatLng(mySetting.latitude, mySetting.longitude)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, 10f))
        googleMap.addMarker(
            MarkerOptions()
                .position(defaultPosition)
                .title("Moi")
                .icon(greenIcon)
        )?.setTag("MOI")

        SetActivity()

        googleMap.setOnInfoWindowClickListener { marker ->
            val pkGuid = marker.getTag().toString()
            if(pkGuid != "MOI") {
                var chat: Chat? = itemsChatShow.find { it.id == pkGuid }
                // ajouter pour naviger vers le chat detail en envoyer chat
            }
        }
    }

    private fun SetActivity() {
        itemsChatShow.forEach { itemChat: Chat ->
            if (itemChat.gpsCoordinates.latitude != null && itemChat.gpsCoordinates.longitude != null) {
                val position = itemChat.gpsCoordinates.toLatLng()
                val radiusInMeters = itemChat.radius.toDouble() // Utilisez la valeur de radius

                val circleOptions = CircleOptions()
                    .center(position)
                    .radius(radiusInMeters)
                    .strokeWidth(2f)
                    .strokeColor(Color.BLUE)
                    .fillColor(Color.argb(128, itemChat.color.r, itemChat.color.g, itemChat.color.b)) // Couleur de remplissage semi-transparente

                googleMap.addCircle(circleOptions)
                    .tag = itemChat.id

            }
        }
    }



    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}

private fun GpsCoordinates.toLatLng(): LatLng {
    return LatLng(latitude.toDouble(), longitude.toDouble());
}
