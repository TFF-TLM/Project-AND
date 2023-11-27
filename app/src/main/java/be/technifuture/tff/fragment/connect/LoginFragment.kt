package be.technifuture.tff.fragment.connect

import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.JeuxActivity
import be.technifuture.tff.databinding.FragmentLoginBinding
import be.technifuture.tff.service.AlertDialogCustom
import be.technifuture.tff.service.AlertDialogCustom.ErrorValidation
import be.technifuture.tff.service.NetworkService

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.buttonCreateUser.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToCreateUserFragment()
            findNavController().navigate(direction)
        }

        binding.buttonLogin.setOnClickListener {
            binding.loaderView.visibility = View.VISIBLE
            val isValid = NetworkService.user.getUserByLogin(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.text.toString())


            if(isNetworkAvailable()){
                val userFound = isValid
                if(userFound != null){
                    //TODO: Délai lors de la connection. Mettre un loader.
                    /*val sharedPref: SharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(requireContext())
                        with(sharedPref.edit()) {
                            putString("INFO_USER_USERNAME", )
                            putString("INFO_USER_EMAIL",)
                            putString("INFO_USER_AVATAR",)
                            apply()
                        }*/

                    val intent = Intent(requireContext(), JeuxActivity::class.java)
                    startActivity(intent)
                } else {
                    AlertDialogCustom(requireContext()).getAlert(ErrorValidation.LOG_ERROR)
                    binding.loaderView.visibility = View.GONE
                }
            }else{
                AlertDialogCustom(requireContext()).getAlert(ErrorValidation.NO_CONNECTION)
                binding.loaderView.visibility = View.GONE
            }

        }
        return binding.root
    }

    private fun isNetworkAvailable (): Boolean {
        val connectivityManager = getSystemService(requireContext(), ConnectivityManager:: class.java)
        val currentNetwork = connectivityManager?.activeNetwork
        val networkCapabilities = connectivityManager?.getNetworkCapabilities(currentNetwork)
        return networkCapabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false

    }


}