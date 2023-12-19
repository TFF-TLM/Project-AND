package be.technifuture.tff.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import be.technifuture.tff.adapter.BonusAdapter
import be.technifuture.tff.databinding.FragmentChatInteractionBinding
import be.technifuture.tff.model.*
import be.technifuture.tff.model.enums.BonusType
import be.technifuture.tff.model.interfaces.BonusListener
import be.technifuture.tff.model.interfaces.JeuxListener
import be.technifuture.tff.service.AlertDialogCustom
import be.technifuture.tff.service.network.manager.AuthDataManager
import be.technifuture.tff.service.network.manager.GameDataManager
import be.technifuture.tff.utils.alert.AlertBuilder
import com.squareup.picasso.Picasso


class ChatInteractionFragment(val id: String) : Fragment(), BonusListener {
    private var jeuxListenner: JeuxListener? = null
    private lateinit var binding: FragmentChatInteractionBinding
    private lateinit var adapter: BonusAdapter
    private var chat: Chat? = null
    private lateinit var bonus: MutableList<Bonus>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatInteractionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loaderView.visibility = View.VISIBLE
        GameDataManager.instance.getCatById(id.toInt()) { cat, _, code ->
            binding.loaderView.visibility = View.GONE
            if (code == 200) {
                cat?.let {
                    chat = it.chat
                    SetupRecyclerView()
                    SetupUI()
                    SetupListenner()
                }
            } else {
                Log.d("CAT", "Unable to retrieve cat.")
            }
        }
    }


    public fun setOnButtonClickListener(listenner: JeuxListener) {
        jeuxListenner = listenner
    }

    public fun SetupListenner() {
        binding.btnChatClose.setOnClickListener {
            jeuxListenner?.onClosePopUp()
        }
    }

    private fun SetupUI() {
        chat?.let {
            Picasso.get()
                .load(it.urlImage)
                .into(binding.chatImage)
            Log.d("PROGRESS", "${it.vie}/${it.maxVie}")
            binding.chatVie.progress = it.vie
            binding.chatVie.max = it.maxVie
            binding.levelTxt.text = "Level : " + it.level
            binding.infoCroquetteTxt.text = textFoodPossibility()
            binding.nameTxt.text = it.nom
        }
    }

    private fun SetupRecyclerView() {
        bonus = mutableListOf(
            Bonus(
                BonusType.Croquette,
                AuthDataManager.instance.user.nbCroquette,
                "ico_food"
            )
        )
        adapter = BonusAdapter(bonus, this)
        binding.chatRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.chatRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    private fun updateRecycler() {
        bonus = mutableListOf(
            Bonus(
                BonusType.Croquette,
                AuthDataManager.instance.user.nbCroquette,
                "ico_food"
            )
        )
        adapter.BonusItemsListe = bonus
        adapter.notifyDataSetChanged()
    }

    private fun updateUI() {
        GameDataManager.instance.getCatById(id.toInt()) { cat, _, code ->
            binding.loaderView.visibility = View.GONE
            if (code == 200) {
                cat?.let {
                    chat = it.chat
                    if (it.chat.alive) {
                        updateRecycler()
                        SetupUI()
                    } else {
                        activity?.let { context ->
                            AlertBuilder.messageAlert(
                                context,
                                "Le chat est mort.",
                                "Bravo, vous avez éliminé un chat ennemi !"
                            )
                        }
                        jeuxListenner?.onClosePopUp()
                    }
                }
            } else {
                Log.d("CAT", "Unable to retrieve cat.")
            }
        }
    }

    private fun foodPossibility(): Int {
        return AuthDataManager.instance.user.croquetteMax - (chat?.foodreceived ?: 0)
    }

    private fun textFoodPossibility(): String {
        val food = foodPossibility()
        var text = if (food > 0) {
            "Vous pouvez encore donner jusqu'à $food croquette(s)"
        } else {
            "Vous ne pouvez plus donner de croquettes"
        }
        if (!isSameClan()) {
            text += " empoisonnée(s)"
        }
        return text
    }

    private fun foodMax(): Int {
        val possible = foodPossibility()
        val userFood = AuthDataManager.instance.user.nbCroquette
        return if (possible > userFood) userFood else possible

    }

    private fun isSameClan(): Boolean {
        return (chat?.clan?.id ?: 0) == AuthDataManager.instance.user.clan.id
    }

    override fun onBonusClick(action: String, item: Bonus) {
        Log.d("LM", "onBonusClick")
        activity?.let {
            if (foodPossibility() > 0) {
                if (AuthDataManager.instance.user.nbCroquette > 0) {
                    val titre =
                        if (isSameClan()) "Nourrissez le chat !" else "Empoisonnez le chat !"
                    AlertBuilder.pickerNumberAlert(
                        it,
                        titre,
                        "Choisissez le nombre de croquettes que vous souhaitez donner.",
                        0,
                        foodMax()
                    ) { food ->
                        chat?.let { cat ->
                            binding.loaderView.visibility = View.VISIBLE
                            GameDataManager.instance.feedCat(cat.id.toInt(), food) { code ->
                                if (code == 200) {
                                    updateUI()
                                } else {
                                    binding.loaderView.visibility = View.GONE
                                    AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.CANT_LEVEL_UP)
                                }
                            }
                        }
                    }
                } else {
                    AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.NO_MORE_FOOD)
                }
            } else {
                AlertDialogCustom(it).getAlert(AlertDialogCustom.ErrorValidation.CANT_FEED)
            }
        }
    }

}