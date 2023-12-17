package be.technifuture.tff.fragment

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentJeuxBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.enums.ChoixPopUp
import be.technifuture.tff.model.enums.ColorChoice
import be.technifuture.tff.model.interfaces.*
import be.technifuture.tff.repos.*
import be.technifuture.tff.service.*
import be.technifuture.tff.service.network.manager.GameDataManager
import be.technifuture.tff.utils.alert.AlertBuilder
import be.technifuture.tff.utils.location.LocationManager
import com.google.android.gms.maps.MapView

class JeuxFragment : Fragment(), JeuxListener {

    private lateinit var binding: FragmentJeuxBinding
    private lateinit var mapView: MapView

    private lateinit var orientationArrow: OrientationArrow

    private var gpsCoordinatesUser: GpsCoordinates? = null
    private var gpsCoordinatesTarget: GpsCoordinates? = null
    private var gpsCoordinatesLastCall: GpsCoordinates? = null

    private var locationManager: LocationManager? = null
    private val limiteMeterToCall = 5f

    var joystick: Joystick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJeuxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ReposGoogleMap.getInstance().init(20f, this)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(ReposGoogleMap.getInstance())
        gpsCoordinatesUser =
            LocationManager.instance[LocationManager.KEY_LOCATION_MANAGER]?.localisationUser

        InitJoystick()
        OnInitListener()
        updateBtnJoystickVisibility()

        orientationArrow = OrientationArrow()
        gpsCoordinatesUser?.let {
            UpdateUiGps(it)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun OnInitListener() {
        binding.BtnShop.setOnClickListener {

            val direction = JeuxFragmentDirections.actionJeuxFragmentToMarketFragment()
            findNavController().navigate(direction)
        }
        binding.BtnAddChat.setOnClickListener {
            OpenPopUp(ChoixPopUp.AddChat, null)
        }

        binding.BtnRadar.setOnClickListener {
            OpenPopUp(ChoixPopUp.Radar, null)
        }

        binding.BtnProfil.setOnClickListener {
            val direction = JeuxFragmentDirections.actionJeuxFragmentToProfileFragment()
            findNavController().navigate(direction)
        }

        binding.BtnJoystick.setOnClickListener {
            GameDataManager.instance.isModeDemo = !GameDataManager.instance.isModeDemo
            updateBtnJoystickVisibility()
        }


        binding.relativeLayoutJoystick.setOnTouchListener { arg0, arg1 ->
            joystick!!.drawStick(arg1)

            if (GameDataManager.instance.isModeDemo && gpsCoordinatesUser != null) {

                if (arg1.action == MotionEvent.ACTION_DOWN || arg1.action == MotionEvent.ACTION_MOVE) {
                    gpsCoordinatesUser = ReposGoogleMap.getInstance().calculateNewPosition(
                        gpsCoordinatesUser!!,
                        joystick!!.getAngle().toDouble(),
                        0.00002
                    )
                    //0.0002
                    ReposGoogleMap.getInstance()
                        .setPosition(gpsCoordinatesUser!!, ColorChoice.Yellow)
                } else if (arg1.action == MotionEvent.ACTION_UP) {
                    ReposGoogleMap.getInstance()
                        .setPosition(gpsCoordinatesUser!!, ColorChoice.Green)
                }

                locationManager?.localisationUser = gpsCoordinatesUser

                if (shouldCall(limiteMeterToCall)) {
                    gpsCoordinatesUser?.let {
                        ReposGoogleMap.getInstance().updateCatsAndPoints(
                            it.latitude.toFloat(),
                            it.longitude.toFloat()
                        ) { lat, lon ->
                            if (!GameDataManager.instance.isLaunch) {
                                GameDataManager.instance.isLaunch = true
                                updateVisibilityBtnRadar(lat, lon)
                            }
                        }
                        gpsCoordinatesLastCall = it
                    }
                }
                UpdateUiGps(gpsCoordinatesUser!!)
            }
            true
        }
    }

    private fun updateBtnJoystickVisibility() {
        if (GameDataManager.instance.isModeDemo) {
            binding.relativeLayoutJoystick.visibility = View.VISIBLE
            binding.BtnJoystick.alpha = 1.0F
            //binding.BtnJoystick.setColorFilter(Color.rgb(80, 255, 80)) // Assurez-vous que votre couleur est définie correctement
        } else {
            binding.relativeLayoutJoystick?.visibility = View.GONE
            binding.BtnJoystick.alpha = 0.3F
            //binding.BtnJoystick.setColorFilter(Color.rgb(255, 80, 80)) // Assurez-vous que votre couleur est définie correctement
        }
    }

    //******************************************************** Joystick
    private fun InitJoystick() {
        joystick = Joystick(requireContext(), binding.relativeLayoutJoystick, R.drawable.inner)
        joystick!!.setStickSize(150, 150)
        joystick!!.setLayoutSize(300, 300)
        joystick!!.setLayoutAlpha(200)
        joystick!!.setStickAlpha(100)
        joystick!!.setOffset(90)
        joystick!!.setMinimumDistance(20)
    }


    //******************************************************** Events Users


    override fun onChatOpenned(id: String) {
        OpenPopUp(ChoixPopUp.Chat, id)
    }

    fun OpenPopUp(PopUpType: ChoixPopUp, catId: String? = null) {
        binding.FragmentChat.visibility = View.VISIBLE

        var chatInteractionFragment: Fragment? = null

        if (PopUpType == ChoixPopUp.Chat && catId != null) {

            chatInteractionFragment = ChatInteractionFragment(catId)
            chatInteractionFragment.setOnButtonClickListener(this)
        }

        if (PopUpType == ChoixPopUp.Radar) {
            chatInteractionFragment = RadarFragment()
            chatInteractionFragment.setOnButtonClickListener(this)
        }

        if (PopUpType == ChoixPopUp.AddChat) {
            chatInteractionFragment = ChatAPoserFragment()
            chatInteractionFragment.setOnButtonClickListener(this)
        }

        val fadeIn = Fade()
        fadeIn.duration = 800

        if (chatInteractionFragment != null) {
            chatInteractionFragment.enterTransition = fadeIn
            childFragmentManager.beginTransaction()
                .replace(R.id.FragmentChat, chatInteractionFragment)
                .commit()
        }
    }


    override fun onClosePopUp() {
        val fadeOut = Fade()
        fadeOut.duration = 500

        val existingFragment = childFragmentManager.findFragmentById(R.id.FragmentChat)

        existingFragment?.let {
            it.exitTransition = fadeOut
            childFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }

    override fun onInterestOpenned(id: String) {
        binding.FragmentChat.visibility = View.INVISIBLE
        val existingFragment = childFragmentManager.findFragmentById(R.id.FragmentChat)
        existingFragment?.let {
            childFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
        binding.loaderView.visibility = View.VISIBLE
        GameDataManager.instance.interactInterestPoint(id.toInt()) { cat, food, _, code ->
            binding.loaderView.visibility = View.GONE
            activity?.let {
                if (code == 200) {
                    cat?.let { chat ->
                        AlertBuilder.messageAlert(
                            it,
                            "Bravo, vous avez récupéré un chat !",
                            "Allez dans l'onglet mes chats pour le voir et le poser sur la carte."
                        )
                    } ?: run {
                        AlertBuilder.messageAlert(
                            it,
                            "Des croquettes !",
                            "Vous avez gagnés $food croquette(s)"
                        )
                    }
                    LocationManager.instance[LocationManager.KEY_LOCATION_MANAGER]?.localisationUser?.let { gps ->
                        UpdateUiGps(gps)
                    }
                } else {
                    AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.ALREADY_INTERACT)
                }
            }
        }

    }

    private fun shouldCall(limite: Float): Boolean {
        var distance = limite
        gpsCoordinatesUser?.let { user ->
            gpsCoordinatesLastCall?.let { lastCall ->
                val results = FloatArray(1)
                Location.distanceBetween(
                    user.latitude,
                    user.longitude,
                    lastCall.latitude,
                    lastCall.longitude,
                    results
                )
                distance = results[0]
            }
        }
        return distance >= limite
    }

    private fun updateOnGpsChanged(gpsCoordinates: GpsCoordinates) {
        if (!GameDataManager.instance.isModeDemo) {
            gpsCoordinatesUser = gpsCoordinates
            ReposGoogleMap.getInstance().setPosition(gpsCoordinates, ColorChoice.Green)

            if (shouldCall(limiteMeterToCall)) {
                ReposGoogleMap.getInstance().updateCatsAndPoints(
                    gpsCoordinates.latitude.toFloat(),
                    gpsCoordinates.longitude.toFloat()
                ) { lat, lon ->
                    if (!GameDataManager.instance.isLaunch) {
                        GameDataManager.instance.isLaunch = true
                        updateVisibilityBtnRadar(lat, lon)
                    }
                }
                gpsCoordinatesLastCall = gpsCoordinates
            }

            UpdateUiGps(gpsCoordinates)
        }
    }

    private fun setupLocationManager() {
        activity?.let {
            LocationManager(
                it,
                null,
                LocationManager.LOCATION_PERMISSION_REQUEST_CODE,
                LocationManager.KEY_LOCATION_MANAGER
            )
        }
        LocationManager.instance[LocationManager.KEY_LOCATION_MANAGER]?.let {
            locationManager = it
            it.setCallback { location ->
                Log.d("LM", "callback")
                updateOnGpsChanged(GpsCoordinates(location.latitude, location.longitude))
            }
        }
    }

    private fun updateVisibilityBtnRadar(lat: Float, lon: Float) {
        if (GameDataManager.instance.getNearCats(lat, lon).isNotEmpty()) {
            Log.d("RADAR", "Btn radar visible")
            binding.BtnRadar.visibility = View.VISIBLE
        } else {
            Log.d("RADAR", "Btn radar gone")
            binding.BtnRadar.visibility = View.GONE
        }
    }

    private fun UpdateUiGps(gpsCoordinates: GpsCoordinates) {
        if (GameDataManager.instance.catFromUserInBag.isEmpty()) {
            binding.BtnAddChat.visibility = View.GONE
        } else {
            binding.BtnAddChat.visibility = View.VISIBLE
        }
        mySetting.LocalisationGps = gpsCoordinates as GpsCoordinates
        gpsCoordinatesTarget = GameDataManager.instance.getNearestCats(
            gpsCoordinates.latitude.toFloat(),
            gpsCoordinates.longitude.toFloat()
        )?.chat?.gpsCoordinates

        updateVisibilityBtnRadar(
            gpsCoordinates.latitude.toFloat(),
            gpsCoordinates.longitude.toFloat()
        )

        if (gpsCoordinatesUser != null && gpsCoordinatesTarget != null) {
            binding.imgCompas.rotation = orientationArrow.updateArrowRotationDemo1(
                gpsCoordinates!!, gpsCoordinatesTarget!!
            )

            (childFragmentManager.findFragmentById(R.id.FragmentChat) as? GpsUpadateListener)?.onGpsChanged(
                gpsCoordinates
            )
        }
    }

    //******************************************************** Events UI
    override fun onResume() {
        setupLocationManager()
        if (::mapView.isInitialized) {
            mapView.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        locationManager?.removeUpdates()
        ReposGoogleMap.getInstance().resetMyMarker()
        if (::mapView.isInitialized) {
            mapView.onPause()
        }
        super.onPause()
    }

    override fun onDestroy() {
        locationManager?.removeUpdates()
        if (::mapView.isInitialized) {
            mapView.onDestroy()
        }
        super.onDestroy()
    }

    override fun onLowMemory() {
        if (::mapView.isInitialized) {
            mapView.onLowMemory()
        }
        super.onLowMemory()
    }

}