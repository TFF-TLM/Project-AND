package be.technifuture.tff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ZoneChat
import be.technifuture.tff.model.interfaces.AddChatListener
import com.squareup.picasso.Picasso

class AddChatAdapter(
    private val ChatsItemsListe: List<ZoneChat>,
    private val onClickListener: AddChatListener
) : RecyclerView.Adapter<AddChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddChatViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_add_chat, parent, false)
        return AddChatViewHolder(layout, onClickListener)
    }

    override fun onBindViewHolder(holder: AddChatViewHolder, position: Int) {
        val item: Chat = ChatsItemsListe[position].chat
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
    val image: ImageView = view.findViewById(R.id.CellAddChatImg)

    fun setupData(item: Chat) {

        if (!item.urlImage.isNullOrEmpty()) {
            Picasso.get()
                .load(item.urlImage)
                .into(image)
        }

        BtnAdd.setOnClickListener {
            onClickListener.onAddChatClick("UPD", item)
        }

    }


}

