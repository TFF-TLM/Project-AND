package be.technifuture.tff.adapter

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
import be.technifuture.tff.model.interfaces.AddChatListener
import com.bumptech.glide.Glide

class AddChatAdapter(
    private val ChatsItemsListe: MutableList<Chat>,
    private val onClickListener: AddChatListener
) : RecyclerView.Adapter<AddChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddChatViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_add_chat, parent, false)
        return AddChatViewHolder(layout, onClickListener)
    }

    override fun onBindViewHolder(holder: AddChatViewHolder, position: Int) {
        val item: Chat = ChatsItemsListe[position]
        holder.setupData(item)
    }

    override fun getItemCount(): Int {
        return ChatsItemsListe.size
    }
}


class AddChatViewHolder (
    private val view: View,
    private val onClickListener: AddChatListener
) : RecyclerView.ViewHolder(view) {

    val BtnAdd: ImageButton = view.findViewById(R.id.CellAddChatBtn)
    val nom: TextView = view.findViewById(R.id.CellAddChatNom)
    val image: ImageView = view.findViewById(R.id.CellAddChatImg)

    fun setupData(item: Chat) {
        nom.text = item.nom

        if (!item.urlImage.isNullOrEmpty()) {
            Glide.with(view)
                .load(item.urlImage) // Chargez l'URL de l'image
                .into(image) // Affichez l'image dans la vue 'photo'
        }

        BtnAdd.setOnClickListener {
            onClickListener.onAddChatClick("UPD", item)
        }

    }


}

