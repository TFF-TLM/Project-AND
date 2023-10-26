package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.databinding.FragmentCreateUserBinding
import be.technifuture.tff.fragment.connect.viewController.CreateUserController


class CreateUserFragment : Fragment() {

    private lateinit var binding: FragmentCreateUserBinding
    private lateinit var viewController: CreateUserController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateUserBinding.inflate(layoutInflater)
        viewController = CreateUserController(binding)

        binding.buttonCreateUser.setOnClickListener {
            viewController.validateForm()
        }
        return binding.root
    }
}

