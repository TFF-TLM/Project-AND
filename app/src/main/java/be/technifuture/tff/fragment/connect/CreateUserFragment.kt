package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.R
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
        binding.header.title.text = getString(R.string.info_user)

        binding.buttonCreateUser.setOnClickListener {

            viewController.validateForm { user ->
                val direction = CreateUserFragmentDirections.actionCreateUserFragmentToCreateClanUserFragment(user)
                findNavController().navigate(direction)
            }
        }
        return binding.root
    }
}

