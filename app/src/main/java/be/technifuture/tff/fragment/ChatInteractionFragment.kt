package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.adapter.BonusAdapter
import be.technifuture.tff.databinding.FragmentChatInteractionBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.interfaces.BonusListener
import be.technifuture.tff.repos.ReposUser
import com.bumptech.glide.Glide

class ChatInteractionFragment : Fragment(), BonusListener {
    private lateinit var binding: FragmentChatInteractionBinding
    private val args: ChatInteractionFragmentArgs by navArgs()
    private lateinit var adapter : BonusAdapter
    private lateinit var chat: Chat
    private lateinit var bonus : MutableList<Bonus>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatInteractionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SetupRecyclerView()
        chat = args.chat!!
        Log.d("LM", chat.toString())
        SetupUI();
        binding.btnChatClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun SetupUI(){

        if (!chat.urlImage.isNullOrEmpty()) {
            Glide.with(this)
                .load(chat.urlImage) // Chargez l'URL de l'image
                .into(binding.chatImage) // Affichez l'image dans la vue 'photo'
        }

        binding.chatVie.progress = chat.vie
        binding.chatVie.max = chat.maxVie
        binding.levelTxt.text = "Level : " + chat.level
    }

    private fun SetupRecyclerView(){
        bonus = ReposUser.getInstance().mockData().bonus
        adapter = BonusAdapter(bonus, this)
        binding.chatRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        //(requireActivity(), RecyclerView.HORIZONTAL,false)
        binding.chatRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onBonusClick(action: String, item: Bonus) {
        TODO("Not yet implemented")
    }

}