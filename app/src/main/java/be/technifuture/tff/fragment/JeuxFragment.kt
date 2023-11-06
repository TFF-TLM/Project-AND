package be.technifuture.tff.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.databinding.FragmentJeuxBinding
import be.technifuture.tff.repos.ReposGoogleMap
import com.google.android.gms.maps.MapView

class JeuxFragment : Fragment() {
    private lateinit var binding: FragmentJeuxBinding
    private lateinit var mapView: MapView
    private lateinit var reposGoogleMap: ReposGoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJeuxBinding.inflate(layoutInflater)

        reposGoogleMap = ReposGoogleMap(13f)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(reposGoogleMap)

        return binding.root
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