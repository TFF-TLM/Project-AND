package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import be.technifuture.tff.adapter.CreateClanAdapter
import be.technifuture.tff.databinding.FragmentCreateClanUserBinding
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.service.NetworkService

class CreateClanUserFragment : Fragment() {

    private val args: CreateClanUserFragmentArgs by navArgs()
    private lateinit var user: NewUserModel
    private lateinit var binding: FragmentCreateClanUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateClanUserBinding.inflate(layoutInflater)
        setupRecyclerView()
        user = args.user
        binding.nextButton.setOnClickListener {
            val direction =
                CreateClanUserFragmentDirections.actionFragmentCreateClanUserToCreateAvatarUserFragment(
                    user
                )
            findNavController().navigate(direction)
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewChooseClan.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewChooseClan.adapter = CreateClanAdapter(NetworkService.clan.getClan()) { id ->
            binding.nextButton.visibility = View.VISIBLE
            user.clan = id
        }
    }
}