package be.technifuture.tff.fragment.connect

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentCreateClanUserBinding
import be.technifuture.tff.model.NewUserModel

class CreateClanUserFragment : Fragment() {

    private val args: CreateClanUserFragmentArgs by navArgs()
    private lateinit var user: NewUserModel
    private lateinit var binding: FragmentCreateClanUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateClanUserBinding.inflate(layoutInflater)
        user = args.user
        
        binding.sayHello.text = getString(R.string.say_hello, user.login)
        binding.clan1Contenair.setOnClickListener {
            selectedContenair(1)
        }
        binding.clan2Contenair.setOnClickListener {
            selectedContenair(2)
        }
        binding.clan3Contenair.setOnClickListener {
            selectedContenair(3)
        }

        binding.header.title.text = getString(R.string.choose_clan)

        binding.nextButton.setOnClickListener {
            val direction =
                CreateClanUserFragmentDirections.actionFragmentCreateClanUserToCreateAvatarUserFragment(
                    user
                )
            findNavController().navigate(direction)
        }
        return binding.root
    }

    private fun selectedContenair(selected: Int) {
        when(selected){
            1 -> {
                binding.clan1Contenair.setBackgroundColor(Color.YELLOW)
                binding.clan2Contenair.setBackgroundColor(Color.TRANSPARENT)
                binding.clan3Contenair.setBackgroundColor(Color.TRANSPARENT)
            }
            2-> {
                binding.clan1Contenair.setBackgroundColor(Color.TRANSPARENT)
                binding.clan2Contenair.setBackgroundColor(Color.RED)
                binding.clan3Contenair.setBackgroundColor(Color.TRANSPARENT)
            }
            3 ->{
                binding.clan1Contenair.setBackgroundColor(Color.TRANSPARENT)
                binding.clan2Contenair.setBackgroundColor(Color.TRANSPARENT)
                binding.clan3Contenair.setBackgroundColor(Color.GREEN)
            }
        }
        user.clan = selected
        binding.nextButton.visibility = View.VISIBLE
    }
}