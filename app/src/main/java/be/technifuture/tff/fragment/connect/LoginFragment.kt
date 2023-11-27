package be.technifuture.tff.fragment.connect

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.JeuxActivity
import be.technifuture.tff.databinding.FragmentLoginBinding
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.AlertDialogCustom
import be.technifuture.tff.service.AlertDialogCustom.ErrorValidation
import be.technifuture.tff.service.NetworkService
import java.util.Date

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPref: SharedPreferences

    private val configID = "USER_ID"
    private val configTime = "TIMESTAMP_ID"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())

        binding.buttonCreateUser.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToCreateUserFragment()
            findNavController().navigate(direction)
        }

        isYetConnected()

        binding.buttonLogin.setOnClickListener {
            binding.loaderView.visibility = View.VISIBLE

            NetworkService.user.getUserByLogin(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.text.toString()
            ) { user ->

                if (isNetworkAvailable()) {
                    if (user != null) {
                        navigate(user)
                    } else {
                        AlertDialogCustom(requireContext()).getAlert(ErrorValidation.LOG_ERROR)
                        binding.loaderView.visibility = View.GONE
                    }
                } else {
                    AlertDialogCustom(requireContext()).getAlert(ErrorValidation.NO_CONNECTION)
                    binding.loaderView.visibility = View.GONE
                }
            }

        }
        return binding.root
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(requireContext(), ConnectivityManager::class.java)
        val currentNetwork = connectivityManager?.activeNetwork
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(currentNetwork)
        return networkCapabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false

    }

    private fun isYetConnected() {
        val userId = sharedPref.getInt(configID, -1)
        val timestamp = sharedPref.getInt(configTime, 0)

        if (timestamp < Date().time && userId != -1) {
            NetworkService.user.getUserById(userId) { user ->
                navigate(user)
            }
        }
        Log.d("DEBUGG", userId.toString())

    }

    private fun navigate(user: UserModel) {
        with(sharedPref.edit()) {
            putInt(configID, user.id)
            // TimeStamp Actuel + 1 semaine
            putLong(configTime, Date().time + (60 * 60 * 24 * 7))
            apply()
        }
        val intent = Intent(requireContext(), JeuxActivity::class.java)
        startActivity(intent)
    }


}