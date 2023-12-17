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
import be.technifuture.tff.service.NetworkService

class HistoriqueFragment : Fragment() {

    lateinit var binding: FragmentHistoriqueBinding
    private var chats: MutableList<Chat> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initMockChat()
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

        val recyclerViewTop = binding.topRecycler
        val recyclerViewBot = binding.bottomRecycler

        recyclerViewTop.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewTop.adapter = HistoriqueChatAdapter(mutableListOf(chats[1], chats[2]))

        recyclerViewBot.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewBot.adapter = HistoriqueChatAdapter(mutableListOf(chats[0]))


    }

    private fun initMockChat() {
        chats = mutableListOf(
            Chat(
                "0",
                "https://res.cloudinary.com/dota5mahf/4f23f2b4-31bb-4543-b5b3-0bd397c422e7",
                "Lucy",
                3,
                5,
                1,
                true,
                GpsCoordinates(0.0, 0.0),
                1,
                NetworkService.clan.getClanById(1),
                "Tony",
                true,
                40
            ),
            Chat(
                "2",
                "https://res.cloudinary.com/dota5mahf/5cadc418-82d1-47c9-904b-068be3b59073",
                "Sushi",
                5,
                10,
                2,
                true,
                GpsCoordinates(0.0, 0.0),
                1,
                NetworkService.clan.getClanById(2),
                "Medhi",
                true,
                40
            ),
            Chat(
                "0",
                "https://res.cloudinary.com/dota5mahf/b690df13-ed11-4a21-b587-e3af75c597ae",
                "Padmé",
                34,
                50,
                3,
                true,
                GpsCoordinates(0.0, 0.0),
                1,
                NetworkService.clan.getClanById(3),
                "Laurent",
                true,
                40
            ),
        )
    }
}