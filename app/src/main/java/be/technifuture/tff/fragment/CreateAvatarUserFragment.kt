package be.technifuture.tff.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.technifuture.tff.R

class CreateAvatarUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_create_avatar_user, container, false)
    }
}


private val chooseAvatar = listOf(
    listOf("Astronaute", "Twitcher", "Pilote", "Aventurier", "Journaliste",
        "Docteur", "Pompier", "Super Héro", "Avocat", "President"),
    listOf("Serpent", "Chient", "Licorne", "Dragon", "Hibou",
        "Aigle", "Araignée", "Pieuvre", "Requin", "Tigre"),
    listOf("Football", "Jeu vidéo", "Judo", "Natation", "Vélo",
        "Tik Tok", "...", "...", "...", "...")
)
