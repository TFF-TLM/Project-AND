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
import be.technifuture.tff.model.interfaces.RadarCellListener
import be.technifuture.tff.model.interfaces.RadarListener
import com.bumptech.glide.Glide

class RadarChatsAdapter(
    var ChatsItemsListe: MutableList<Chat>,
    private val onClickListener: RadarListener
) : RecyclerView.Adapter<RadarChatsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadarChatsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_radar_chat, parent, false)
        return RadarChatsViewHolder(layout, onClickListener)
    }

    override fun onBindViewHolder(holder: RadarChatsViewHolder, position: Int) {
        val item: Chat = ChatsItemsListe[position]
        holder.setupData(item)
    }

    override fun getItemCount(): Int {
        return ChatsItemsListe.size
    }
}


class RadarChatsViewHolder (
    private val view: View,
    private val onClickListener: RadarListener
) : RecyclerView.ViewHolder(view) {

    val nom: TextView = view.findViewById(R.id.CellRadarChatNom)
    val distance: TextView = view.findViewById(R.id.CellRadarChatDistance)
    val photo: ImageView = view.findViewById(R.id.img_cat)
    val level: TextView = view.findViewById(R.id.CellRadarChatLevel)
    val hp: ProgressBar = view.findViewById(R.id.CellRadarChatHP)

    fun setupData(item: Chat) {
        hp.max = item.maxVie
        hp.progress = item.vie
        nom.text = item.nom
        distance.text = item.distanceFromUser.toString() + " m"
        level.text  = item.level.toString()

        if (!item.urlImage.isNullOrEmpty()) {
            Glide.with(view)
                .load(item.urlImage) // Chargez l'URL de l'image
                .into(photo) // Affichez l'image dans la vue 'photo'
        }

        /*
        BtnOpen.setOnClickListener {
            onClickListener.onRadarClick("UPD", item)
        }
        */
    }


}

