package be.technifuture.tff.fragment.connect

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.JeuxActivity
import be.technifuture.tff.R
import be.technifuture.tff.adapter.CreateAvatarAdapter
import be.technifuture.tff.databinding.FragmentCreateAvatarUserBinding
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.service.AlertDialogCustom
import be.technifuture.tff.service.network.dto.Register
import be.technifuture.tff.service.network.dto.UserDataRequestBody
import be.technifuture.tff.service.network.manager.AuthDataManager
import com.squareup.picasso.Picasso

class CreateAvatarUserFragment : Fragment() {

    private val args: CreateAvatarUserFragmentArgs by navArgs()
    private lateinit var user: NewUserModel
    private lateinit var binding: FragmentCreateAvatarUserBinding
    private val authManager = AuthDataManager.instance

    private var answerSelected = mutableListOf("", "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAvatarUserBinding.inflate(layoutInflater)
        binding.buttonCreateUser.setOnClickListener { navigate() }
        binding.buttonGenerateAvatar.setOnClickListener { register() }
        binding.header.title.text = getString(R.string.choose_clan)

        binding.question1.text = getString(R.string.question1)
        setupRecyclerView(chooseAvatar[0], binding.recyclerViewQ1, 1)

        binding.question2.text = getString(R.string.question2)
        setupRecyclerView(chooseAvatar[1], binding.recyclerViewQ2, 2)

        binding.question3.text = getString(R.string.question3)
        setupRecyclerView(chooseAvatar[2], binding.recyclerViewQ3, 3)

        user = args.user
        binding.sayHello.text = getString(R.string.say_hello, user.login)

        return binding.root
    }

    private fun setupRecyclerView(
        list: MutableList<String>,
        recyclerView: RecyclerView,
        qSelected: Int
    ) {
        recyclerView.layoutManager =
            GridLayoutManager(context,3)
        recyclerView.adapter = CreateAvatarAdapter(list) { item ->
            insertAnswer(item, qSelected)
        }
    }

    private fun insertAnswer(answer: String, question: Int) {
        answerSelected[question - 1] = answer

        when (question) {
            1 -> {
                binding.answer1.text = answer
                binding.answer1.visibility = View.VISIBLE
                binding.recyclerViewQ1.visibility = View.GONE
            }

            2 -> {
                binding.answer2.text = answer
                binding.answer2.visibility = View.VISIBLE
                binding.recyclerViewQ2.visibility = View.GONE
            }

            3 -> {
                binding.answer3.text = answer
                binding.answer3.visibility = View.VISIBLE
                binding.recyclerViewQ3.visibility = View.GONE
            }
        }
        if (!answerSelected.contains("")) {
            binding.buttonGenerateAvatar.visibility = View.VISIBLE
        }
    }

    private fun navigate() {
        val intent = Intent(requireContext(), JeuxActivity::class.java)
        startActivity(intent).also {
            activity?.finish()
        }
    }

    private fun register() {
        binding.loaderView.visibility = View.VISIBLE
        authManager.register(
            Register(user.login, user.mail, user.password),
            UserDataRequestBody(user.clan, answerSelected[0], answerSelected[1], answerSelected[2])
        ) { user, errorRegister, _, codeRegister, _, _ ->
            errorRegister?.let { error ->
                if (codeRegister == 400) {
                    error.username?.isNotEmpty()?.let {
                        activity?.let { AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.LOGIN_EXIST) }
                    }
                    error.email?.isNotEmpty()?.let {
                        activity?.let { AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.MAIL_EXIST) }
                    }
                }
            }
            user?.let {
                Picasso.get()
                    .load(it.urlAvatar)
                    .into(binding.avatarUser)
                binding.loaderView.visibility = View.GONE
                binding.boxOfRecycler.visibility = View.GONE
                binding.buttonGenerateAvatar.visibility = View.GONE
                binding.buttonCreateUser.visibility = View.VISIBLE
            }
        }
    }
}

private val chooseAvatar = mutableListOf(
    mutableListOf( // Lieux
        "Espace", "Montagne", "Plage", "Maison",
        "Forêt", "Désert", "Campagne", "Lune", "Mer"
    ),
    mutableListOf( // Animaux
        "Serpent", "Licorne", "Dragon", "Hibou", "Phoenix",
        "Aigle", "Requin", "Tigre", "Krakken"
    ),
    mutableListOf( // Hobbit
        "Football", "Jeu vidéo", "Judo", "Natation", "Vélo",
        "Basket", "Voiture", "Courrir", "Hockey"
    )
)