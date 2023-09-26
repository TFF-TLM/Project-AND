package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentCreateUserBinding

class CreateUserFragment : Fragment() {

    lateinit var binding: FragmentCreateUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateUserBinding.inflate(layoutInflater)
        return binding.root
    }
}