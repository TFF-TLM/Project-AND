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

class AdapterRadarChats(
    private val ChatsItemsListe: MutableList<Chat>,
    private val onLikeClickListener: OnLikeClickListener
) : RecyclerView.Adapter<SortieListeViewHolder1>() {

    interface OnLikeClickListener {
        fun onLikeClick(action:String, item: Chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortieListeViewHolder1 {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_radar_chat, parent, false)
        return SortieListeViewHolder1(layout, onLikeClickListener)
    }

    override fun onBindViewHolder(holder: SortieListeViewHolder1, position: Int) {
        val item: Chat = ChatsItemsListe[position]
        holder.setupData(item)
    }

    override fun getItemCount(): Int {
        return ChatsItemsListe.size
    }
}


class SortieListeViewHolder1(
    private val view: View,
    private val onLikeClickListener: AdapterRadarChats.OnLikeClickListener
) : RecyclerView.ViewHolder(view) {

    val nom: TextView = view.findViewById(R.id.CellRadarChatNom)
    val distance: TextView = view.findViewById(R.id.CellRadarChatDistance)
    val photo: ImageView = view.findViewById(R.id.CellRadarChatImage)
    val level: TextView = view.findViewById(R.id.CellRadarChatLevel)
    val hp: ProgressBar = view.findViewById(R.id.CellRadarChatHP)

    fun setupData(item: Chat) {
        hp.max = 100
        hp.progress = item.vie
        nom.text = item.mom
        distance.text = "50 M"
        level.text  = item.level.toString()
Log.d("LM",item.toString())

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

