package be.technifuture.tff.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.adapter.RadarChatsAdapter
import be.technifuture.tff.databinding.FragmentRadarBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.interfaces.GpsUpadateListener
import be.technifuture.tff.model.interfaces.JeuxListener
import be.technifuture.tff.model.interfaces.OrientationListener
import be.technifuture.tff.model.interfaces.RadarListener
import be.technifuture.tff.repos.ReposZoneChat
import be.technifuture.tff.service.OrientationManager
import be.technifuture.tff.service.network.manager.GameDataManager
import be.technifuture.tff.utils.location.LocationManager
import be.technifuture.tff.view.*
import kotlin.math.cos
import kotlin.math.sin


class RadarFragment : Fragment(), RadarListener, OrientationListener, GpsUpadateListener {

    private lateinit var binding: FragmentRadarBinding
    private lateinit var radarView: RadarView
    private var gpsCoordinatesUser: GpsCoordinates? =
        LocationManager.instance[LocationManager.KEY_LOCATION_MANAGER]?.localisationUser

    private var jeuxListenner: JeuxListener? = null

    private var chats: MutableList<Chat> = mutableListOf()
    private lateinit var adapter: RadarChatsAdapter
    private lateinit var compasManager: OrientationManager
    val objects: MutableList<ObjectData> = mutableListOf()

    private fun InitChats() {
        gpsCoordinatesUser?.let {
            chats = getCats(it)
        }
    }

    private fun getCats(gps: GpsCoordinates): MutableList<Chat> {
        return GameDataManager.instance.getNearCats(
            gps.latitude.toFloat(),
            gps.longitude.toFloat()
        ).map { it.toChat() }.toMutableList()
    }

    //******************************************************** Events UI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadarBinding.inflate(layoutInflater)
        InitChats()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radarView = requireActivity().findViewById(R.id.radarView)
        compasManager = OrientationManager(requireContext(), this)
        gpsCoordinatesUser = mySetting.LocalisationGps
        SetupRecyclerView()
        SetupListenner()

        chats.forEach { chat ->
            chat.distanceFromUser =
                ReposZoneChat.getInstance().getDistance(gpsCoordinatesUser!!, chat.gpsCoordinates!!)
                    .toInt()
        }
    }


    //******************************************************** Events Close
    public fun setOnButtonClickListener(listenner: JeuxListener) {
        jeuxListenner = listenner
    }

    public fun SetupListenner() {
        binding.btnRadarClose.setOnClickListener {
            jeuxListenner?.onClosePopUp()
        }
    }

    //******************************************************** Listenner
    private fun SetupRecyclerView() {
        chats.let { it ->
            adapter = RadarChatsAdapter(it, this)
            binding.RadarRecyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            binding.RadarRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


    //******************************************************** Events Radar


    override fun onOrientationChanged(azimuth: Float, pitch: Float, roll: Float) {
        updateRadar(azimuth)
    }

    private fun updateRadar(azimuth: Float) {

        if (gpsCoordinatesUser != null) {
            objects.clear()
            chats.forEach { item ->
                val localCoordinates = convertGpsToXY(gpsCoordinatesUser!!, item.gpsCoordinates!!)
                val rotatedCoordinates = rotateCoordinates(localCoordinates, azimuth, 0.04F)
                val objectData =
                    ObjectData(rotatedCoordinates.x.toFloat(), rotatedCoordinates.y.toFloat())
                objects.add(objectData)
            }
        }
        radarView.updateObjects(objects)
    }

    private fun rotateCoordinates(
        coordinates: LocalCoordinates,
        azimuth: Float,
        sensitivity: Float
    ): LocalCoordinates {
        val scaledAzimuth = azimuth * sensitivity
        val x = coordinates.x * cos(scaledAzimuth) - coordinates.y * sin(scaledAzimuth)
        val y = coordinates.x * sin(scaledAzimuth) + coordinates.y * cos(scaledAzimuth)
        return LocalCoordinates(x, y)
    }


    private fun convertGpsToXY(source: GpsCoordinates, target: GpsCoordinates): LocalCoordinates {
        val earthRadius = 6371000.0
        val lat1 = Math.toRadians(source.latitude)
        val lon1 = Math.toRadians(source.longitude)
        val lat2 = Math.toRadians(target.latitude)
        val lon2 = Math.toRadians(target.longitude)

        val deltaLon = lon2 - lon1
        val deltaLat = lat2 - lat1

        val x = earthRadius * deltaLon * Math.cos(lat1)
        val y =
            earthRadius * Math.log(Math.tan(Math.PI / 4 + lat2 / 2) / Math.tan(Math.PI / 4 + lat1 / 2))

        return LocalCoordinates(x, y)
    }

    override fun onRadarClick(action: String, item: Bonus) {
        TODO("Not yet implemented")
    }

    //******************************************************** Events UI
    override fun onResume() {
        compasManager = OrientationManager(requireContext(), this)
        super.onResume()
    }

    override fun onPause() {
        compasManager.unregister()
        super.onPause()
    }

    override fun onDestroy() {
        compasManager.unregister()
        super.onDestroy()
    }

    override fun onGpsChanged(gpsCoordinatesUser: GpsCoordinates) {
        this.gpsCoordinatesUser = gpsCoordinatesUser
        chats = getCats(gpsCoordinatesUser)
        chats.forEach { chat ->
            chat.distanceFromUser =
                ReposZoneChat.getInstance()
                    .getDistance(gpsCoordinatesUser!!, chat.gpsCoordinates!!)
                    .toInt()
        }
        adapter.ChatsItemsListe = chats
        adapter.notifyDataSetChanged()
    }

}
