package be.technifuture.tff.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
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
import com.google.android.gms.maps.MapView

class JeuxFragment : Fragment(), JeuxListener, GpsUpadateListener {

    private lateinit var binding: FragmentJeuxBinding
    private lateinit var mapView: MapView

    private var isModeDemo: Boolean = false
    private lateinit var orientationArrow: OrientationArrow

    private var gpsCoordinatesUser: GpsCoordinates? = GpsCoordinates(50.5926493,5.5539429)
    private var gpsCoordinatesTarget: GpsCoordinates? = null

    var joystick : Joystick? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJeuxBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ReposGoogleMap.getInstance().Init(requireContext(),13f, this)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(ReposGoogleMap.getInstance())
        ReposLacolisation.getInstance().setListenner(this)

        InitJoystick()
        OnInitListener()

        if(ReposUser.getInstance().getChatNb() > 0) {
            binding.BtnAddChat.visibility = View.GONE
        } else {
            binding.BtnAddChat.visibility = View.VISIBLE
        }
        orientationArrow = OrientationArrow()
        binding.BtnRadar.visibility = View.GONE
        binding.BtnAddChat.visibility =  View.INVISIBLE
        ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser!!, ColorChoice.Green)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun OnInitListener(){
        binding.BtnAddChat.setOnClickListener {

        }

        binding.BtnRadar.setOnClickListener {
            OpenPopUp(ChoixPopUp.Radar, null)
            //val action = JeuxFragmentDirections.actionJeuxFragmentToRadarFragment()
            //findNavController().navigate(action)
        }

        binding.BtnProfil.setOnClickListener {

        }

        binding.BtnJoystick.setOnClickListener {
            isModeDemo = !isModeDemo
            if (isModeDemo) {
                binding.relativeLayoutJoystick.visibility = View.VISIBLE
                binding.BtnJoystick.setColorFilter(Color.rgb(80, 255, 80)) // Assurez-vous que votre couleur est définie correctement
            } else {
                binding.relativeLayoutJoystick?.visibility = View.GONE
                binding.BtnJoystick.setColorFilter(Color.rgb(255, 80, 80)) // Assurez-vous que votre couleur est définie correctement
            }
        }

        binding.relativeLayoutJoystick.setOnTouchListener { arg0, arg1 ->
             joystick!!.drawStick(arg1)

             if(isModeDemo == true && gpsCoordinatesUser != null) {
                 if (arg1.action == MotionEvent.ACTION_DOWN || arg1.action == MotionEvent.ACTION_MOVE) {
                     gpsCoordinatesUser = ReposGoogleMap.getInstance().CalculateNewPosition(
                         gpsCoordinatesUser!!,
                         joystick!!.getAngle().toDouble(),
                         0.0004
                     )
                     //0.0002
                     ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser!!, ColorChoice.Yellow)
                 } else if (arg1.action == MotionEvent.ACTION_UP) {
                     ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser!!, ColorChoice.Green)
                 }
                 UpdateCompatRadar()
             }
            true
        }
    }

    //******************************************************** Joystick
    fun InitJoystick(){
        joystick = Joystick(requireContext(), binding.relativeLayoutJoystick, R.drawable.inner)
        joystick!!.setStickSize(150,150)
        joystick!!.setLayoutSize(300,300)
        joystick!!.setLayoutAlpha(200)
        joystick!!.setStickAlpha(100)
        joystick!!.setOffset(90)
        joystick!!.setMinimumDistance(20)
    }

    //******************************************************** Events Users


    override fun onChatOpenned(chat: Chat) {
        OpenPopUp(ChoixPopUp.Chat, chat)
    }

    fun OpenPopUp(PopUpType:ChoixPopUp ,chat: Chat? = null){
        binding.FragmentChat.visibility = View.VISIBLE

        var chatInteractionFragment: Fragment? = null

        if(PopUpType == ChoixPopUp.Chat && chat != null) {
            val bundle = Bundle()
            bundle.putParcelable("chat", chat)
            chatInteractionFragment = ChatInteractionFragment()
            chatInteractionFragment.arguments = bundle
            chatInteractionFragment.setOnButtonClickListener(this)
        }

        if(PopUpType == ChoixPopUp.Radar) {
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

    override fun onInterestOpenned(pointInteret: PointInteret) {
        binding.FragmentChat.visibility = View.INVISIBLE
        val existingFragment = childFragmentManager.findFragmentById(R.id.FragmentChat)
        existingFragment?.let {
            childFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
    }

    //******************************************************** delegate
    override fun onGpsChanged(gpsCoordinates: GpsCoordinates) {
        if(isModeDemo == false){
            gpsCoordinatesUser = gpsCoordinates
            UpdateCompatRadar()
            ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser!!, ColorChoice.Green)
            (childFragmentManager.findFragmentById(R.id.FragmentChat) as? GpsUpadateListener)?.onGpsChanged(gpsCoordinates)
        }
    }

    fun UpdateCompatRadar(){

        mySetting.LocalisationGps = gpsCoordinatesUser as GpsCoordinates

        gpsCoordinatesTarget = null;
        gpsCoordinatesTarget = ReposZoneChat.getInstance().getNearChat(gpsCoordinatesUser!!)

        binding.imgCompas.rotation = orientationArrow.updateArrowRotationDemo1(
            gpsCoordinatesUser!!, gpsCoordinatesTarget!!
        )

        if(ReposZoneChat.getInstance().nearChats.count() > 0) {
            binding.BtnRadar.visibility = View.VISIBLE
        } else {
            binding.BtnRadar.visibility = View.GONE
        }

    }


    //******************************************************** Events UI
    override fun onResume() {
        ReposLacolisation.getInstance().setListenner(this)
        if (::mapView.isInitialized) {
            mapView.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        ReposLacolisation.getInstance().onResumed()
        if (::mapView.isInitialized) {
            mapView.onPause()
        }
        super.onPause()
    }

    override fun onDestroy() {
        ReposLacolisation.getInstance().onResumed()
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