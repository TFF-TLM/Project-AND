package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.transition.Fade
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentJeuxBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.enums.ColorChoice
import be.technifuture.tff.model.interfaces.*
import be.technifuture.tff.repos.ReposGoogleMap
import be.technifuture.tff.repos.ReposLacolisation
import be.technifuture.tff.service.Joystick
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

class JeuxFragment : Fragment(), JeuxListener, GpsUpadateListener {
    private lateinit var binding: FragmentJeuxBinding
    private lateinit var mapView: MapView
    private lateinit var gpsCoordinatesUser: GpsCoordinates

    var relativeLayoutJoystick : RelativeLayout? = null
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
    }


    //******************************************************** Joystick
    fun InitJoystick(){

        relativeLayoutJoystick = binding.relativeLayoutJoystick as RelativeLayout

        joystick = Joystick(requireContext(), relativeLayoutJoystick!!, R.drawable.inner)
        joystick!!.setStickSize(150,150)
        joystick!!.setLayoutSize(300,300)
        joystick!!.setLayoutAlpha(200)
        joystick!!.setStickAlpha(100)
        joystick!!.setOffset(90)
        joystick!!.setMinimumDistance(20)

        relativeLayoutJoystick!!.setOnTouchListener { arg0, arg1 ->
            joystick!!.drawStick(arg1)
            if (arg1.action == MotionEvent.ACTION_DOWN || arg1.action == MotionEvent.ACTION_MOVE) {
                gpsCoordinatesUser = calculateNewPosition(gpsCoordinatesUser, joystick!!.getAngle().toDouble())
                ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser, ColorChoice.Yellow)
            } else if (arg1.action == MotionEvent.ACTION_UP) {
                ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser, ColorChoice.Green)
            }
            true
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

    //******************************************************** Events Users

    private fun calculateNewPosition(oldPosition: GpsCoordinates, angle: Double): GpsCoordinates {
        val vitesse = 0.0002
        val angleInRadians = Math.toRadians(angle)
        val newLatitude = oldPosition.latitude + vitesse * -Math.sin(angleInRadians)
        val newLongitude = oldPosition.longitude + vitesse * Math.cos(angleInRadians)
        return GpsCoordinates(newLatitude, newLongitude)
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

    override fun onGpsChanged(gpsCoordinates: GpsCoordinates) {
        gpsCoordinatesUser = gpsCoordinates
        ReposGoogleMap.getInstance().SetPosition(gpsCoordinatesUser, ColorChoice.Green)
    }

}