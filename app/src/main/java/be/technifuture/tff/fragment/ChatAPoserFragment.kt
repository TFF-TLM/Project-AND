package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.adapter.AddChatAdapter
import be.technifuture.tff.databinding.FragmentChatAPoserBinding
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.interfaces.AddChatListener
import be.technifuture.tff.model.interfaces.JeuxListener
import be.technifuture.tff.service.AlertDialogCustom
import be.technifuture.tff.service.network.manager.GameDataManager
import be.technifuture.tff.utils.alert.AlertBuilder

class ChatAPoserFragment : Fragment(), AddChatListener {

    private lateinit var binding: FragmentChatAPoserBinding
    private var jeuxListenner: JeuxListener? = null
    private lateinit var adapter: AddChatAdapter
    private var chats = GameDataManager.instance.catFromUserInBag
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatAPoserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.btnAddChatClose.setOnClickListener {
            jeuxListenner?.onClosePopUp()
        }
    }

    //******************************************************** Listenner

    private fun setupRecyclerView() {
        chats.let { chatList ->
            adapter = AddChatAdapter(chatList, this)
            binding.RecyclerViewMySchat.apply {
                layoutManager = GridLayoutManager(
                    requireActivity(),
                    2,  // Nombre de colonnes
                    RecyclerView.VERTICAL,
                    false
                )
            }
            binding.RecyclerViewMySchat.adapter = adapter
        }
    }

    //******************************************************** Events Close
    public fun setOnButtonClickListener(listenner: JeuxListener) {
        jeuxListenner = listenner
    }

    override fun onAddChatClick(action: String, item: Chat) {
        Log.d("DROP", "${item.id.toInt()}")
        activity?.let {
            AlertBuilder.inputAlert(it, "Poser un chat", "Donnez un nom à votre chat !") { input ->
                if (input.isNotEmpty() || input.isNotBlank()) {
                    binding.loaderView.visibility = View.VISIBLE
                    GameDataManager.instance.dropCat(item.id.toInt(), input) { code ->
                        binding.loaderView.visibility = View.GONE
                        if (code == 200) {
                            jeuxListenner?.onClosePopUp()
                        } else {
                            AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.CANT_DROP_CAT)
                        }
                    }
                } else {
                    AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.NAME_CAT_EMPTY)
                }
            }
        }
    }

}