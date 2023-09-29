package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentRadarBinding
import be.technifuture.tff.view.GpsCoordinates
import be.technifuture.tff.view.LocalCoordinates
import be.technifuture.tff.view.ObjectData
import be.technifuture.tff.view.RadarView


class RadarFragment : Fragment() {

    private lateinit var binding: FragmentRadarBinding
    private lateinit var radarView: RadarView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radarView = requireActivity().findViewById(R.id.radarView)

        val radarPosition = GpsCoordinates(50.6160754324028, 5.503717766285025)
        val gpsCoordinates = listOf(
            GpsCoordinates(50.6149283418158, 5.501153571992362),
            GpsCoordinates(50.616116263548136, 5.504683378057636),
            GpsCoordinates(50.615551238020196, 5.5039752430043976)
        )


        val objects : MutableList<ObjectData> = mutableListOf()
        gpsCoordinates.forEach { item ->
            val localCoordinates = convertGpsToXY(item, radarPosition)
            objects.add(ObjectData(localCoordinates.x.toFloat(), localCoordinates.y.toFloat()))
        }

        radarView.updateObjects(objects)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadarBinding.inflate(layoutInflater)
        return binding.root
    }


    fun convertGpsToXY(gpsCoordinates: GpsCoordinates, radarPosition: GpsCoordinates): LocalCoordinates {
        val earthRadius = 6371000.0
        val lat1 = Math.toRadians(radarPosition.latitude)
        val lon1 = Math.toRadians(radarPosition.longitude)
        val lat2 = Math.toRadians(gpsCoordinates.latitude)
        val lon2 = Math.toRadians(gpsCoordinates.longitude)

        val deltaLon = lon2 - lon1
        val deltaLat = lat2 - lat1

        val x = earthRadius * deltaLon * Math.cos(lat1)
        val y = earthRadius * Math.log(Math.tan(Math.PI / 4 + lat2 / 2) / Math.tan(Math.PI / 4 + lat1 / 2))

        return LocalCoordinates(x, y)
    }
}
