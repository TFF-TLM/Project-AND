package be.technifuture.tff.fragment

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentRadarBinding
import be.technifuture.tff.view.ObjectData
import be.technifuture.tff.view.RadarView


class RadarFragment : Fragment() {

    private lateinit var binding: FragmentRadarBinding
    private lateinit var radarView: RadarView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radarView = requireActivity().findViewById(R.id.radarView)

        // Exemple d'ajout de quelques objets
        val objects = mutableListOf(
            ObjectData(150f, Math.toRadians(45.0)),
            ObjectData(200f, Math.toRadians(30.0)),
            ObjectData(250f, Math.toRadians(90.0))
        )
        radarView.updateObjects(objects)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadarBinding.inflate(layoutInflater)

        return binding.root
    }
}