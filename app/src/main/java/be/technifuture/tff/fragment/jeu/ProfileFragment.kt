package be.technifuture.tff.fragment.jeu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentProfileBinding
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.network.manager.AuthDataManager
import be.technifuture.tff.service.network.manager.GameDataManager
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var user: UserModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI(AuthDataManager.instance.user)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateUI(user: UserModel) {
        Picasso.get()
            .load(user.urlAvatar)
            .into(binding.imgAvatar)

        binding.imgClan.setImageResource(user.clan.image)

        binding.header.logo.layoutParams.height = binding.header.logo.layoutParams.height / 3
        binding.header.title.visibility = View.GONE

        binding.labelLogin.text = user.login
        binding.labelClan.text = user.clan.name

        binding.labelNiv.text = getString(R.string.level, user.level.toString())
        binding.labelExp.text = getString(
            R.string.expAffiche,
            user.expActuel.toString(), user.expMax.toString()
        )

        binding.nbrCroquette.text =
            getString(R.string.nbCroquet, user.nbCroquette.toString())
        binding.nbrCatMap.text =
            getString(R.string.nbCatMap, GameDataManager.instance.catFromUserOnMap.size.toString())
        binding.nbrCatBag.text =
            getString(R.string.nbCatBag, GameDataManager.instance.catFromUserInBag.size.toString())

        binding.BtnHistorique.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileFragmentToHistoriqueFragment()
            findNavController().navigate(direction)
        }

        binding.BtnClose.setOnClickListener {
            val direction = ProfileFragmentDirections.actionProfileFragmentToJeuxFragment()
            findNavController().navigate(direction)
        }

        val widthMax = binding.backExp.layoutParams.width
        val ratio = (user.expActuel.toDouble() / user.expMax.toDouble())
        //val ratio = 0.5
        binding.frontExp.layoutParams.width = (widthMax * if (ratio > 0.1) ratio else 0.1).toInt()
    }

}