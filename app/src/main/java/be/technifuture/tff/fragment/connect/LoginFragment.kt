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
import be.technifuture.tff.service.UserConnected
import be.technifuture.tff.service.network.manager.AuthDataManager
import java.util.Date
import be.technifuture.tff.service.network.dto.Auth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPref: SharedPreferences
    private val authManager = AuthDataManager.instance

    private val configID = "USER_ID"
    private val configTime = "TIMESTAMP_ID"
    private val timeToReconnect = (60 * 60 * 24 * 7) // 1 semaine

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

        binding.labelRetrievePassword.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToRetrieveMailFragment()
            findNavController().navigate(direction)
        }

        binding.header.title.visibility = View.GONE

        binding.buttonLogin.setOnClickListener {
/*            binding.loaderView.visibility = View.VISIBLE

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
            }*/
            login()
        }
        //TODO: Fait planter l'app
        //isYetConnected()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isAlreadyConnected()
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
        val timestamp = sharedPref.getLong(configTime, 0)

        if (timestamp < Date().time && userId != -1) {
            NetworkService.user.getUserById(userId) { user ->
                navigate(user)
            }
        }
        Log.d("DEBUGG", userId.toString())

    }

    private fun isAlreadyConnected() {
        authManager.isAlreadyConnected(binding.loaderView) { user, error, code ->
            if (user != null && error == null && code == 200) {
                navigate(user)
            }
        }
    }

    private fun login() {
        binding.loaderView.visibility = View.VISIBLE
        if (isNetworkAvailable()) {
            authManager.login(
                Auth(
                    binding.editTextLogin.text.toString(),
                    binding.editTextPassword.text.toString()
                ),
                binding.checkSave.isChecked
            ) { user, error, code ->
                if (user != null && error == null && code == 200) {
                    navigate(user)
                } else {
                    activity?.let { AlertDialogCustom(it).getAlert(ErrorValidation.LOG_ERROR) }
                    binding.loaderView.visibility = View.GONE
                }
            }
        } else {
            activity?.let { AlertDialogCustom(it).getAlert(ErrorValidation.NO_CONNECTION) }
            binding.loaderView.visibility = View.GONE
        }
    }

    private fun navigate(user: UserModel) {
        /*        if(binding.checkSave.isChecked){
                    with(sharedPref.edit()) {
                        putInt(configID, user.id)
                        putLong(configTime, Date().time + timeToReconnect)
                        apply()
                        Log.d("DEBUGG", "fct Sauvegarde, Login Fragment : On sauvegarde")
                    }
                }*/
        UserConnected.user = user
        UserConnected.clan = NetworkService.clan.getClanById(user.clan)

        val intent = Intent(requireContext(), JeuxActivity::class.java)
        startActivity(intent).also {
            activity?.finish()
        }
    }
}