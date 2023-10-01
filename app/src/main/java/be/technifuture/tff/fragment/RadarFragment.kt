package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.adapter.AdapterRadarChats
import be.technifuture.tff.databinding.FragmentRadarBinding
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.view.LocalCoordinates
import be.technifuture.tff.view.ObjectData
import be.technifuture.tff.view.RadarView


class RadarFragment : Fragment(), AdapterRadarChats.OnLikeClickListener {

    private lateinit var binding: FragmentRadarBinding
    private lateinit var radarView: RadarView

    private var chats: MutableList<Chat>? = mutableListOf()
    private lateinit var adapter : AdapterRadarChats

    private fun InitChats(){
        chats?.add(Chat("1","https://www.zooplus.be/magazine/wp-content/uploads/2019/06/comprendre-le-langage-des-chats-1024x768.jpg","Chat 1 ", 80, 5,GpsCoordinates(50.6149283418158, 5.501153571992362)))
        chats?.add(Chat("2","https://miaoubox.s3.eu-central-1.amazonaws.com/blog/chat%20yeux%20final.jpg","Chat 2 ", 50, 8,GpsCoordinates(50.616116263548136, 5.504683378057636)))
        chats?.add(Chat("3","https://t3t8k6v8.rocketcdn.me/wp-content/uploads/2020/05/Chat-qui-petrit-petrissage.jpg","Chat 3 ", 10, 15,GpsCoordinates(50.615551238020196, 5.5039752430043976)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radarView = requireActivity().findViewById(R.id.radarView)

        val radarPosition = GpsCoordinates(50.6160754324028, 5.503717766285025)
        val objects : MutableList<ObjectData> = mutableListOf()
        chats?.forEach { item ->
            val localCoordinates = convertGpsToXY(item.gpsCoordinates, radarPosition)
            objects.add(ObjectData(localCoordinates.x.toFloat(), localCoordinates.y.toFloat()))
        }

        SetupRecyclerView()
        radarView.updateObjects(objects)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadarBinding.inflate(layoutInflater)
        InitChats()
        return binding.root
    }

    private fun SetupRecyclerView(){
        chats?.let { it ->
            adapter = AdapterRadarChats(it, this)
            binding.RadarRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL,false)
            binding.RadarRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
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

    override fun onLikeClick(action: String, item: Chat) {
        TODO("Not yet implemented")
    }
}
