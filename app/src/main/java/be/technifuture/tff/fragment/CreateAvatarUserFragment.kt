package be.technifuture.tff.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.databinding.FragmentCreateAvatarUserBinding

class CreateAvatarUserFragment : Fragment() {

    lateinit var binding: FragmentCreateAvatarUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAvatarUserBinding.inflate(layoutInflater)

        initRadioGroup()
        binding.buttonCreateUser.setOnClickListener { createUser() }
        binding.buttonGenerateAvatar.setOnClickListener { createAvatar() }

        return binding.root
    }

    private fun initRadioGroup() {

        var rand = (chooseAvatar[0].indices).random()
        binding.item11.text = chooseAvatar[0][rand]
        chooseAvatar[0].removeAt(rand)

        rand = (chooseAvatar[0].indices).random()
        binding.item12.text = chooseAvatar[0][rand]
        chooseAvatar[0].removeAt(rand)

        rand = (chooseAvatar[0].indices).random()
        binding.item13.text = chooseAvatar[0][rand]
        chooseAvatar[0].removeAt(rand)

        rand = (chooseAvatar[0].indices).random()
        binding.item14.text = chooseAvatar[0][rand]
        chooseAvatar[0].removeAt(rand)



        rand = (chooseAvatar[1].indices).random()
        binding.item21.text = chooseAvatar[1][rand]
        chooseAvatar[1].removeAt(rand)

        rand = (chooseAvatar[1].indices).random()
        binding.item22.text = chooseAvatar[1][rand]
        chooseAvatar[1].removeAt(rand)

        rand = (chooseAvatar[1].indices).random()
        binding.item23.text = chooseAvatar[1][rand]
        chooseAvatar[1].removeAt(rand)

        rand = (chooseAvatar[1].indices).random()
        binding.item24.text = chooseAvatar[1][rand]
        chooseAvatar[1].removeAt(rand)
    }

    private fun createUser() {
        //TODO:
        // 1. Vérifier que l'avatar à bien été crée
        // 2. Récuperer les infos del'eran précédent
        // 3. L'envoyé à l'API
    }

    private fun createAvatar() {
        //TODO :
        // 1. Verifier que les 3 questions on été répondu
        // 2. Demander la création de l'avatar
        // 3. Récuperer l'image et la mettre en entête
    }
}


private val chooseAvatar = mutableListOf(
    mutableListOf(
        "Astronaute", "Twitcher", "Pilote", "Aventurier", "Journaliste",
        "Docteur", "Pompier", "Super Héro", "Avocat", "President"
    ),
    mutableListOf(
        "Serpent", "Chient", "Licorne", "Dragon", "Hibou",
        "Aigle", "Araignée", "Pieuvre", "Requin", "Tigre"
    ),
    mutableListOf(
        "Football", "Jeu vidéo", "Judo", "Natation", "Vélo",
        "Tik Tok", "...", "...", "...", "..."
    )
)
