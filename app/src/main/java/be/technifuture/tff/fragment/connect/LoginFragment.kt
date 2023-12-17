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
import be.technifuture.tff.service.network.manager.AuthDataManager
import java.util.Date
import be.technifuture.tff.service.network.dto.Auth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authManager = AuthDataManager.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

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
            login()
        }
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

    private fun isAlreadyConnected() {
        authManager.isAlreadyConnected(binding.loaderView) { user, error, code ->
            if (user != null && error == null && code == 200) {
                navigate()
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
                    navigate()
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

    private fun navigate() {
        val intent = Intent(requireContext(), JeuxActivity::class.java)
        startActivity(intent).also {
            activity?.finish()
        }
    }
}