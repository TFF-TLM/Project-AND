package be.technifuture.tff.fragment.jeu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.adapter.HistoriqueChatAdapter
import be.technifuture.tff.adapter.RadarChatsAdapter
import be.technifuture.tff.databinding.FragmentHistoriqueBinding
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.interfaces.RadarListener
import be.technifuture.tff.repos.ReposZoneChat
import be.technifuture.tff.service.ClanService
import be.technifuture.tff.service.MockData
import be.technifuture.tff.service.NetworkService

class HistoriqueFragment : Fragment() {

    lateinit var binding: FragmentHistoriqueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoriqueBinding.inflate(layoutInflater)
        binding.header.title.visibility = View.GONE
        binding.header.logo.layoutParams.height = binding.header.logo.layoutParams.height / 3

        binding.BtnProfil.setOnClickListener {
            val direction = HistoriqueFragmentDirections.actionHistoriqueFragmentToProfileFragment()
            findNavController().navigate(direction)
        }
        binding.BtnClose.setOnClickListener {
            val direction = HistoriqueFragmentDirections.actionHistoriqueFragmentToJeuxFragment()
            findNavController().navigate(direction)
        }

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val chats = MockData.getListChat()

        val recyclerViewTop = binding.topRecycler
        val recyclerViewBot = binding.bottomRecycler

        recyclerViewTop.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewTop.adapter = HistoriqueChatAdapter(mutableListOf(chats[1], chats[2]))

        recyclerViewBot.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewBot.adapter = HistoriqueChatAdapter(mutableListOf(chats[0]))


    }
}