package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import be.technifuture.tff.R
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
        //SetupListenner()
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
        binding.FragmentChat.visibility = View.VISIBLE

        val bundle = Bundle()
        bundle.putParcelable("chat", chat)

        val chatInteractionFragment = ChatInteractionFragment()
        chatInteractionFragment.arguments = bundle
        chatInteractionFragment.setOnButtonClickListener(this)

        val fadeIn = Fade()
        fadeIn.duration = 800
        chatInteractionFragment.enterTransition = fadeIn

        childFragmentManager.beginTransaction()
            .replace(R.id.FragmentChat, chatInteractionFragment)
            .commit()

    }

    override fun onClosePopUp() {
        // Utiliser des transitions d'AndroidX
        val fadeOut = Fade()
        fadeOut.duration = 500

        // Rechercher le fragment existant
        val existingFragment = childFragmentManager.findFragmentById(R.id.FragmentChat)

        // Appliquer la transition de fondu lors de la suppression du fragment
        existingFragment?.let {
            it.exitTransition = fadeOut
            childFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }

    override fun onInterestOpenned(pointInteret: PointInteret) {
        Log.d("LM","onInterestOpenned")
        binding.FragmentChat.visibility = View.INVISIBLE
        val existingFragment = childFragmentManager.findFragmentById(R.id.FragmentChat)
        existingFragment?.let {
            childFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }

}