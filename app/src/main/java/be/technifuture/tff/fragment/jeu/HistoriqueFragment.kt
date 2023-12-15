package be.technifuture.tff.fragment.jeu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.adapter.RadarChatsAdapter
import be.technifuture.tff.databinding.FragmentHistoriqueBinding
import be.technifuture.tff.model.Chat
import be.technifuture.tff.repos.ReposZoneChat

class HistoriqueFragment : Fragment() {

    lateinit var binding: FragmentHistoriqueBinding
    private var chats: MutableList<Chat>? = mutableListOf()

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
        binding.BtnClose.setOnClickListener{
            val direction = HistoriqueFragmentDirections.actionHistoriqueFragmentToJeuxFragment()
            findNavController().navigate(direction)
        }
        return binding.root
    }

    private fun SetupRecyclerView(catList: List<Chat>){
    }
}