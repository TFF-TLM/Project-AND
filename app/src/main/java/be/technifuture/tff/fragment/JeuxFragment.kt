package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.databinding.FragmentJeuxBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.interfaces.*
import be.technifuture.tff.repos.ReposGoogleMap
import com.google.android.gms.maps.MapView

class JeuxFragment : Fragment(), LocationChangeListener, JeuxListener {
    private lateinit var binding: FragmentJeuxBinding
    private lateinit var mapView: MapView
    private lateinit var reposGoogleMap: ReposGoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJeuxBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reposGoogleMap = ReposGoogleMap(requireContext(),13f, this)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(reposGoogleMap)
    }

    override fun onLocationChanged(gpsCoordinatesUser: GpsCoordinates) {
        // Faites quelque chose avec les nouvelles coordonnées
        Log.d("LM", "Nouvelles coordonnées onLocationChanged - Latitude: ${gpsCoordinatesUser.latitude}, Longitude: ${gpsCoordinatesUser.longitude}")
        // Ajoutez le code que vous souhaitez exécuter en réponse à un changement de position
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


    override fun onChatOpenned(chat: Chat) {
        val action = JeuxFragmentDirections.actionJeuxFragmentToChatInteractionFragment(chat)
        findNavController().navigate(action)
    }

    override fun onInterestOpenned(pointInteret: PointInteret) {
        Log.d("LM","onInterestOpenned")
    }

}