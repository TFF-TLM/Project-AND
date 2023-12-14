package be.technifuture.tff.fragment

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import be.technifuture.tff.adapter.BonusAdapter
import be.technifuture.tff.databinding.FragmentChatInteractionBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.enums.BonusType
import be.technifuture.tff.model.interfaces.BonusListener
import be.technifuture.tff.repos.ReposUser
import com.bumptech.glide.Glide
import be.technifuture.tff.model.interfaces.JeuxListener
import be.technifuture.tff.service.network.manager.AuthDataManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class ChatInteractionFragment : Fragment(), BonusListener {
    private var jeuxListenner: JeuxListener? = null
    private lateinit var binding: FragmentChatInteractionBinding
    private val args: ChatInteractionFragmentArgs by navArgs()
    private lateinit var adapter : BonusAdapter
    private lateinit var chat: Chat
    private lateinit var bonus : MutableList<Bonus>

    private fun InitBonus(){
        bonus = mutableListOf(
            Bonus(BonusType.Soins, 3, "ico_health"),
            Bonus(BonusType.Croquette, AuthDataManager.instance.user!!.nbCroquette, "ico_food"),
            Bonus(BonusType.Bouclier, 2, "ico_shield")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatInteractionBinding.inflate(layoutInflater)
        InitBonus()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SetupRecyclerView()
        chat = args.chat!!
        //TODO CALL_API Get Stats du chat
        SetupUI();
        SetupListenner()
    }

    public fun setOnButtonClickListener(listenner : JeuxListener){
        jeuxListenner = listenner
    }

    public fun SetupListenner(){
        binding.btnChatClose.setOnClickListener {
            jeuxListenner?.onClosePopUp()
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
        adapter = BonusAdapter(bonus, this)
        binding.chatRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.chatRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onBonusClick(action: String, item: Bonus) {
        if(item.bonusType == BonusType.Croquette){
            var nbCroquetteToSend: Int
            showInputDialog(requireContext(), "Combien de croquette veux tu donner", {
                    enteredText ->
                nbCroquetteToSend = enteredText.toInt()
                Log.d("LM", enteredText.toString())
            })


            /*
            if(nbCroquetteToSend < (croquetteMax + croquetteChat)) {

            }
            */
            //TODO CALL_API
            // pour mettre à jours les infos
            // du chat après croqu
        }
        else {
            showNotImplementedAlert(requireContext())
        }
    }

    fun showInputDialog(context: Context, title: String, callback: (String) -> Unit) {

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val input = EditText(context)
        input.hint = "Saisissez votre nombre ici"
        input.inputType = InputType.TYPE_CLASS_NUMBER
        layout.addView(input)

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setView(layout)

        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            val enteredText = input.text.toString()
            callback.invoke(enteredText)
        }

        alertDialogBuilder.setNegativeButton("Annuler") { dialog, which ->
            dialog.dismiss()
        }


        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    fun showNotImplementedAlert(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Non implémenté")
        alertDialogBuilder.setMessage("Cette fonctionnalité n'est pas encore implémentée.")

        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}