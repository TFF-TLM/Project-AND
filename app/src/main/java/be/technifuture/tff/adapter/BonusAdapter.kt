package be.technifuture.tff.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.model.Chat
import com.bumptech.glide.Glide

class BonusAdapter(
    private val ChatsItemsListe: MutableList<Chat>
) : RecyclerView.Adapter<BonusViewHolder>() {

    interface OnLikeClickListener {
        fun onLikeClick(action:String, item: Chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_bonus, parent, false)
        return BonusViewHolder(layout)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        val item: Chat = ChatsItemsListe[position]
        holder.setupData(item)
    }

    override fun getItemCount(): Int {
        return ChatsItemsListe.size
    }
}


class BonusViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    val photo: ImageView = view.findViewById(R.id.cellChatBonusImage)
    val nombreTxt: TextView = view.findViewById(R.id.cellChatBonusText)

    fun setupData(item: Chat) {
        nombreTxt.text = item.nom

        if (!item.urlImage.isNullOrEmpty()) {
            Glide.with(view)
                .load(item.urlImage) // Chargez l'URL de l'image
                .into(photo) // Affichez l'image dans la vue 'photo'
        }

        /*
        BtnOpen.setOnClickListener {
            onLikeClickListener.onLikeClick("UPD", item)
        }
        */
    }
}

