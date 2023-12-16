package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.adapter.AddChatAdapter
import be.technifuture.tff.adapter.RadarChatsAdapter
import be.technifuture.tff.databinding.FragmentChatAPoserBinding
import be.technifuture.tff.databinding.FragmentRadarBinding
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.interfaces.AddChatListener
import be.technifuture.tff.model.interfaces.JeuxListener
import be.technifuture.tff.model.mySetting
import be.technifuture.tff.repos.ReposZoneChat
import be.technifuture.tff.service.OrientationManager

class ChatAPoserFragment : Fragment(), AddChatListener {

    private lateinit var binding: FragmentChatAPoserBinding
    private var jeuxListenner: JeuxListener? = null
    private lateinit var adapter : AddChatAdapter
    private var chats: MutableList<Chat>? = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatAPoserBinding.inflate(layoutInflater)
        LoadChats()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.btnAddChatClose.setOnClickListener{
            jeuxListenner?.onClosePopUp()
        }
    }
    private fun LoadChats(){
        Log.d("API","CALL API pour charger nos chats")
    }

    //******************************************************** Listenner

    private fun setupRecyclerView() {
        chats?.let { chatList ->
            adapter = AddChatAdapter(chatList, this)
            binding.RecyclerViewMySchat.apply {
                layoutManager = GridLayoutManager(
                    requireActivity(),
                    3,  // Nombre de colonnes
                    RecyclerView.VERTICAL,
                    false
                )
            }
            adapter.notifyDataSetChanged()
        }
    }

    //******************************************************** Events Close
    public fun setOnButtonClickListener(listenner : JeuxListener){
        jeuxListenner = listenner
    }

    override fun onAddChatClick(action: String, item: Chat) {
        Log.d("API","CALL API pour ajouter un chat")
        jeuxListenner?.onClosePopUp()
    }

}