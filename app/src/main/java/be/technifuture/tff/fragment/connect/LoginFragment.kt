package be.technifuture.tff.fragment.connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            val isValid =NetworkService.user.getUserByLogin(
                binding.editTextLogin.text.toString(),
                binding.editTextPassword.text.toString())

            val userFound = isValid
            if(userFound != null){
                //TODO: Délai lors de la connection. Mettre un loader.
                Log.d("DEBUGG","Connection Valid")
                val intent = Intent(requireContext(), JeuxActivity::class.java)
                startActivity(intent)
            } else {
                Log.d("DEBUGG","Connection Invalid")
                AlertDialogCustom(requireContext()).getAlert(ErrorValidation.LOG_ERROR)
                //TODO: Alert d'erreur
            }
        }

        return binding.root
    }


}