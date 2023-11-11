package be.technifuture.tff.fragment.connect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.adapter.CreateAvatarAdapter
import be.technifuture.tff.databinding.FragmentCreateAvatarUserBinding

class CreateAvatarUserFragment : Fragment() {

    private lateinit var binding: FragmentCreateAvatarUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAvatarUserBinding.inflate(layoutInflater)
        binding.buttonCreateUser.setOnClickListener { createUser() }
        binding.buttonGenerateAvatar.setOnClickListener { createAvatar() }

        binding.question1.text = "Quel est votre endroit préférer"
        setupRecyclerView(chooseAvatar[0], binding!!.recyclerViewQ1)

        binding.question2.text = "Quel animal est vous ?"
        setupRecyclerView(chooseAvatar[1], binding!!.recyclerViewQ2)

        binding.question3.text = "Quel est votre Hobbit ?"
        setupRecyclerView(chooseAvatar[2], binding!!.recyclerViewQ3)

        return binding.root
    }

    private fun setupRecyclerView(list: MutableList<String>, recyclerViewQ1: RecyclerView, ) {
        recyclerViewQ1.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewQ1.adapter = CreateAvatarAdapter(list) { item ->
            Log.d("DEBUGG",item)
        }
    }

    private fun createUser() {
        Log.d("DEBUGG","Créate User")
        //TODO:
        // 1. Vérifier que l'avatar à bien été crée
        // 2. Récuperer les infos de l'ecran précédent
        // 3. L'envoyé à l'API
    }

    private fun createAvatar() {
        Log.d("DEBUGG","Créate avatar")
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
        "Serpent", "Chient", "Licorne", "Dragon", "Hibou",
        "Aigle", "Araignée", "Pieuvre", "Requin", "Tigre"
    ),
    mutableListOf( // Hobbit
        "Football", "Jeu vidéo", "Judo", "Natation", "Vélo",
        "Basket", "Voiture", "Courrir", "Combat Spatiaux", "Jeu de societer"
    )
)