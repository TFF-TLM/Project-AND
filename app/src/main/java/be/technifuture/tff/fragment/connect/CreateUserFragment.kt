package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentCreateUserBinding
import be.technifuture.tff.service.NetworkService

class CreateUserFragment : Fragment() {

    lateinit var binding: FragmentCreateUserBinding
    private lateinit var viewController: CreateUserController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateUserBinding.inflate(layoutInflater)
        viewController = CreateUserController(binding)

        binding.buttonCreateUser.setOnClickListener {
            viewController.loginIsValid()
        }
        return binding.root
    }
}

class CreateUserController( val viewBinding: FragmentCreateUserBinding){

    fun loginIsValid(){
        if (NetworkService.user.isLoginAvailable(viewBinding.editTextLogin.text.toString()))
            Log.d("TEST", "Login is Ok")
        else
            Log.d("TEST", "Login is NOk")
    }

}