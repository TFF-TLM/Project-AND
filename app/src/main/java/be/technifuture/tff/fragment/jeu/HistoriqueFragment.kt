package be.technifuture.tff.fragment.jeu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.databinding.FragmentHistoriqueBinding

class HistoriqueFragment : Fragment() {

    lateinit var binding: FragmentHistoriqueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoriqueBinding.inflate(layoutInflater)
        binding.header.title.visibility = View.GONE
        binding.header.logo.layoutParams.height =binding.header.logo.layoutParams.height / 3

        binding.BtnProfil.setOnClickListener {
            val direction = HistoriqueFragmentDirections.actionHistoriqueFragmentToProfileFragment()
            findNavController().navigate(direction)
        }

        return binding.root
    }
}