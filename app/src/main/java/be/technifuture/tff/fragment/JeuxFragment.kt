package be.technifuture.tff.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import be.technifuture.tff.R
import be.technifuture.tff.application.MyApp
import be.technifuture.tff.databinding.FragmentJeuxBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.enums.ChoixPopUp
import be.technifuture.tff.model.enums.ColorChoice
import be.technifuture.tff.model.interfaces.*
import be.technifuture.tff.repos.*
import be.technifuture.tff.service.*
import be.technifuture.tff.service.network.manager.GameDataManager
import be.technifuture.tff.utils.location.LocationManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.MapView

class JeuxFragment : Fragment(), JeuxListener {

    private lateinit var binding: FragmentJeuxBinding
    private lateinit var mapView: MapView

    private var isModeDemo: Boolean = false
    private lateinit var orientationArrow: OrientationArrow

    private var gpsCoordinatesUser: GpsCoordinates? = null
    private var gpsCoordinatesTarget: GpsCoordinates? = null
    private var gpsCoordinatesLastCall: GpsCoordinates? = null

    private var locationManager: LocationManager? = null

    var joystick: Joystick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupLocationManager()
        binding = FragmentJeuxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ReposGoogleMap.getInstance().Init(16f, this)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(ReposGoogleMap.getInstance())

        InitJoystick()
        OnInitListener()


        if (ReposUser.getInstance().getChatNb() == 0) {
            binding.BtnAddChat.visibility = View.GONE
        } else {
            binding.BtnAddChat.visibility = View.VISIBLE
        }
        orientationArrow = OrientationArrow()
        binding.BtnRadar.visibility = View.GONE
        ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser!!, ColorChoice.Green)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun OnInitListener() {
        binding.BtnShop.setOnClickListener {

            val direction = JeuxFragmentDirections.actionJeuxFragmentToMarketFragment()
            findNavController().navigate(direction)
        }
        binding.BtnAddChat.setOnClickListener {

        }

        binding.BtnRadar.setOnClickListener {
            OpenPopUp(ChoixPopUp.Radar, null)
            //val action = JeuxFragmentDirections.actionJeuxFragmentToRadarFragment()
            //findNavController().navigate(action)
        }

        binding.BtnProfil.setOnClickListener {
            val direction = JeuxFragmentDirections.actionJeuxFragmentToProfileFragment()
            findNavController().navigate(direction)
        }

        binding.BtnJoystick.setOnClickListener {
            isModeDemo = !isModeDemo
            if (isModeDemo) {
                binding.relativeLayoutJoystick.visibility = View.VISIBLE
                binding.BtnJoystick.alpha = 1.0F
                //binding.BtnJoystick.setColorFilter(Color.rgb(80, 255, 80)) // Assurez-vous que votre couleur est définie correctement
            } else {
                binding.relativeLayoutJoystick?.visibility = View.GONE
                binding.BtnJoystick.alpha = 0.3F
                //binding.BtnJoystick.setColorFilter(Color.rgb(255, 80, 80)) // Assurez-vous que votre couleur est définie correctement
            }
        }


        binding.relativeLayoutJoystick.setOnTouchListener { arg0, arg1 ->
            joystick!!.drawStick(arg1)

            if (isModeDemo && gpsCoordinatesUser != null) {

                if (arg1.action == MotionEvent.ACTION_DOWN || arg1.action == MotionEvent.ACTION_MOVE) {
                    gpsCoordinatesUser = ReposGoogleMap.getInstance().CalculateNewPosition(
                        gpsCoordinatesUser!!,
                        joystick!!.getAngle().toDouble(),
                        0.0004
                    )
                    //0.0002
                    ReposGoogleMap.getInstance()
                        .SetPosition(gpsCoordinatesUser!!, ColorChoice.Yellow)
                } else if (arg1.action == MotionEvent.ACTION_UP) {
                    ReposGoogleMap.getInstance()
                        .SetPosition(gpsCoordinatesUser!!, ColorChoice.Green)
                }

                if (shouldCall(5f)) {
                    gpsCoordinatesUser?.let {
                        ReposGoogleMap.getInstance().updateCatsAndPoints(
                            it.latitude.toFloat(),
                            it.longitude.toFloat()
                        )
                        gpsCoordinatesLastCall = it
                    }
                }

                gpsCoordinatesTarget = null;
                mySetting.LocalisationGps = gpsCoordinatesUser as GpsCoordinates
                gpsCoordinatesUser?.let { coordUser ->
                    GameDataManager.instance.getNearestCats(
                        coordUser.latitude.toFloat(),
                        coordUser.longitude.toFloat()
                    )?.let { cat ->
                        cat.gpsCoordinates?.let { coordTarget ->
                            gpsCoordinatesTarget = coordTarget
                        }
                    }
                }

                if (gpsCoordinatesTarget != null) {
                    binding.BtnRadar.visibility = View.VISIBLE
                } else {
                    binding.BtnRadar.visibility = View.GONE
                }

                binding.imgCompas.rotation = orientationArrow.updateArrowRotationDemo1(
                    gpsCoordinatesUser, gpsCoordinatesTarget
                )
            }
            true
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
        if (!isModeDemo) {
            gpsCoordinatesUser = gpsCoordinates
            ReposGoogleMap.getInstance().SetPosition(gpsCoordinates, ColorChoice.Green)

            if (shouldCall(5f)) {
                ReposGoogleMap.getInstance().updateCatsAndPoints(
                    gpsCoordinates.latitude.toFloat(),
                    gpsCoordinates.longitude.toFloat()
                )
                gpsCoordinatesLastCall = gpsCoordinates
            }

            if (gpsCoordinatesUser != null && gpsCoordinatesTarget != null) {
                binding.imgCompas.rotation = orientationArrow.updateArrowRotationDemo1(
                    gpsCoordinatesUser!!, gpsCoordinatesTarget!!
                )
            }

            (childFragmentManager.findFragmentById(R.id.FragmentChat) as? GpsUpadateListener)?.onGpsChanged(
                gpsCoordinates
            )
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
                updateOnGpsChanged(GpsCoordinates(location.latitude, location.longitude))
            }
        }
    }

    //******************************************************** Events UI
    override fun onResume() {
        if (::mapView.isInitialized) {
            mapView.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
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