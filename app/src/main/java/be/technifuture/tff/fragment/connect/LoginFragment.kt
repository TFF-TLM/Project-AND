package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.databinding.FragmentLoginBinding

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

        return binding.root
    }


}