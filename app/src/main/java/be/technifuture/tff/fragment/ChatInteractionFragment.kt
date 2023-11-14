package be.technifuture.tff.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.adapter.BonusAdapter
import be.technifuture.tff.databinding.FragmentChatInteractionBinding
import be.technifuture.tff.model.*

class ChatInteractionFragment : Fragment(), BonusAdapter.OnLikeClickListener {
    private lateinit var binding: FragmentChatInteractionBinding
    private lateinit var adapter : BonusAdapter
    private var chats: MutableList<Chat>? = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatInteractionBinding.inflate(layoutInflater)
        InitChats()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SetupRecyclerView()
    }

    private fun InitChats(){
        chats?.add(Chat("1","https://www.zooplus.be/magazine/wp-content/uploads/2019/06/comprendre-le-langage-des-chats-1024x768.jpg","Chat 1 ", 80, 5,  GpsCoordinates(50.6149283418158, 5.501153571992362)))
        chats?.add(Chat("2","https://miaoubox.s3.eu-central-1.amazonaws.com/blog/chat%20yeux%20final.jpg","Chat 2 ", 50, 8, GpsCoordinates(50.616116263548136, 5.504683378057636)))
        chats?.add(Chat("3","https://t3t8k6v8.rocketcdn.me/wp-content/uploads/2020/05/Chat-qui-petrit-petrissage.jpg","Chat 3 ", 10, 15, GpsCoordinates(50.615551238020196, 5.5039752430043976)))
    }


    private fun SetupRecyclerView(){
        chats?.let { it ->
            adapter = BonusAdapter(it)
            binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL,false)
            binding.chatRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onLikeClick(action: String, item: Chat) {
        TODO("Not yet implemented")
    }

}