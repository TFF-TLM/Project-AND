package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.adapter.CreateAvatarAdapter
import be.technifuture.tff.databinding.FragmentCreateAvatarUserBinding

class CreateAvatarUserFragment : Fragment() {

    private val args: CreateAvatarUserFragmentArgs by navArgs()
    private lateinit var binding: FragmentCreateAvatarUserBinding

    private var urlAvatar: String? = null
    private var answerSelected = mutableListOf("","","")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAvatarUserBinding.inflate(layoutInflater)
        binding.buttonCreateUser.setOnClickListener { createUser() }
        binding.buttonGenerateAvatar.setOnClickListener { createAvatar() }

        binding.question1.text = getString(R.string.question1)
        setupRecyclerView(chooseAvatar[0], binding.recyclerViewQ1, 1)

        binding.question2.text = getString(R.string.question2)
        setupRecyclerView(chooseAvatar[1], binding.recyclerViewQ2, 2)

        binding.question3.text = getString(R.string.question3)
        setupRecyclerView(chooseAvatar[2], binding.recyclerViewQ3, 3)

        binding.buttonCreateUser.isActivated = false

        return binding.root
    }

    private fun setupRecyclerView(list: MutableList<String>, recyclerView: RecyclerView, qSelected: Int) {
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = CreateAvatarAdapter(list) { item ->
            insertAnswer(item, qSelected)
        }
    }

    private fun insertAnswer(answer: String, question: Int){
        answerSelected[question-1] = answer

        when(question){
            1 ->
                binding.answer1.text = answer
            2 ->
                binding.answer2.text = answer
            3 ->
                binding.answer3.text = answer
        }
        if(!answerSelected.contains("")){
            binding.buttonGenerateAvatar.visibility = View.VISIBLE
        }
    }

    private fun createUser() {
        Log.d("DEBUGG","*** Créate User")
        if(urlAvatar == null){
            Log.d("DEBUGG","** Pas d'url fait")
        }else{
            val user = args.user
            user.urlAvatar = urlAvatar as String
            //TODO: L'envoyé à l'API
        }
    }

    private fun createAvatar() {
        Log.d("DEBUGG","Créate avatar")
        if(urlAvatar == null){
            binding.loaderView.visibility = View.VISIBLE
            urlAvatar = "url_avatar_depuis_API"
            //binding.buttonCreateUser.visibility = View.VISIBLE
        }else{
            Log.d("DEBUGG","url déjà crée")
        }

        //TODO :
        // 1. Verifier que les 3 questions on été répondu
        // 2. Demander la création de l'avatar
        // 3. Récuperer l'image et la mettre en entête
    }
}

private val chooseAvatar = mutableListOf(
    mutableListOf( // Lieux
        "Espace", "Montagne", "Plage", "Piscine", "Maison",
        "Forêt", "Desert", "Campagne", "Lune", "Mer"
    ),
    mutableListOf( // Animaux
        "Serpent", "Chien", "Licorne", "Dragon", "Hibou",
        "Aigle", "Araignée", "Pieuvre", "Requin", "Tigre",
        "Lion", "Orque", "Quokka", "Phoenix", "Pangolin",
        "Griffon", "Kraken", "Axolotl", "Okapi", "Chimère"
    ),
    mutableListOf( // Hobbit
        "Football", "Jeu vidéo", "Judo", "Natation", "Vélo",
        "Basket", "Voiture", "Courrir", "Combat Spatiaux", "Jeu de societer"
    )
)