package be.technifuture.tff.fragment.jeu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.R
import be.technifuture.tff.databinding.FragmentProfileBinding
import be.technifuture.tff.model.UserModel
import be.technifuture.tff.service.NetworkService
import be.technifuture.tff.service.UserConnected
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        Picasso.get()
            .load(UserConnected.user.urlAvatar)
            .into(binding.imgAvatar)

        binding.imgClan.setImageResource(UserConnected.clan.image)

        binding.labelLogin.text = UserConnected.user.login
        binding.labelClan.text = UserConnected.clan.name

        binding.labelNiv.text = getString(R.string.level, UserConnected.user.level.toString())

        /*binding.labelExp.text = getString(R.string.expAffiche,
            UserConnected.user.expActuel.toString(), UserConnected.user.expMax.toString())*/
        binding.labelExp.text = getString(R.string.expAffiche,
            "100", "200")

        binding.nbrCroquette.text = getString(R.string.nbCroquet, UserConnected.user.nbCroquette.toString())
        binding.nbrCat.text = getString(R.string.nbCat, "5")

        revealExpBar()

        return binding.root
    }

    private fun revealExpBar(){
        val widthMax = binding.backExp.layoutParams.width
        //val ratio = (UserConnected.user.expActuel.toDouble() / UserConnected.user.expMax.toDouble())
        val ratio = (100.toDouble() / 200.toDouble())
        binding.frontExp.layoutParams.width = (widthMax * ratio).toInt()
    }




}