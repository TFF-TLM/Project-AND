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
import be.technifuture.tff.databinding.FragmentHistoriqueBinding
import be.technifuture.tff.model.Chat
import be.technifuture.tff.service.network.manager.GameDataManager
import be.technifuture.tff.utils.date.DateBuilder

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loaderView.visibility = View.VISIBLE
        GameDataManager.instance.refreshDataGameFromUser { _, _, _, _, _, _, _, _, _ ->
            binding.loaderView.visibility = View.GONE
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() {

        val recyclerViewTop = binding.topRecycler
        val recyclerViewBot = binding.bottomRecycler

        recyclerViewTop.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewTop.adapter = HistoriqueChatAdapter(catUserHasInteract().toMutableList())

        recyclerViewBot.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewBot.adapter = HistoriqueChatAdapter(catFromUserOnMap().toMutableList())


    }

    private fun catUserHasInteract(): List<Chat> {
        val result = GameDataManager.instance.catUserHasInteract.map { it.chat }
            .sortedBy { it.interactFromUser?.timestamp ?: DateBuilder.refFutur }
        result.forEach { it.updateDistance() }
        return result
    }

    private fun catFromUserOnMap(): List<Chat> {
        val result = GameDataManager.instance.catFromUserOnMap.map { it.chat }
            .sortedBy { chat ->
                if (chat.allInteract.isNotEmpty()) {
                    chat.allInteract.minBy { it.timestamp }.timestamp
                } else {
                    DateBuilder.refFutur
                }
            }
        result.forEach { it.updateDistance() }
        return result
    }
}