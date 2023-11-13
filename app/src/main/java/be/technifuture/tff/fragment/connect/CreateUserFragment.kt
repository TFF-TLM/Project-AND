package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.databinding.FragmentCreateUserBinding
import be.technifuture.tff.fragment.connect.viewController.CreateUserController
import be.technifuture.tff.service.AlertDialogCustom


class CreateUserFragment : Fragment() {

    private lateinit var binding: FragmentCreateUserBinding
    private lateinit var viewController: CreateUserController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateUserBinding.inflate(layoutInflater)
        viewController = CreateUserController(binding, AlertDialogCustom(requireContext()))

        binding.buttonCreateUser.setOnClickListener {
            val user = viewController.validateForm()
            Log.d("DEBUGG", user.toString())

            if(user != null){
                val direction = CreateUserFragmentDirections.actionCreateUserFragmentToCreateAvatarUserFragment(user)
                findNavController().navigate(direction)
            }
        }
        return binding.root
    }
}

