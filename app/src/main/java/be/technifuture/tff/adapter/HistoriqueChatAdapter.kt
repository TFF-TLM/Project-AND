package be.technifuture.tff.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.databinding.CellCreateAvatarBinding
import be.technifuture.tff.databinding.CellRadarChatBinding
import be.technifuture.tff.model.Chat
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class HistoriqueChatAdapter(private var list: MutableList<Chat>) :

    RecyclerView.Adapter<HistoriqueChatViewHolder>() {
    private lateinit var binding: CellRadarChatBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoriqueChatViewHolder {
        //charge le layout de la cellule
        binding = CellRadarChatBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoriqueChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoriqueChatViewHolder, position: Int) {
        holder.bind(list[position])
    }


    //retourne le nbr d'élément à afficher
    override fun getItemCount(): Int {
        return list.size
    }
}

class HistoriqueChatViewHolder(private var viewBinding: CellRadarChatBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: Chat) {

        viewBinding.CellRadarChatHP.max = item.maxVie
        viewBinding.CellRadarChatHP.progress = item.vie
        viewBinding.CellRadarChatNom.text = item.nom
        viewBinding.CellRadarChatDistance.text = item.distanceFromUser.toString() + " m"
        viewBinding.CellRadarChatLevel.text  = item.level.toString()

        if (!item.urlImage.isNullOrEmpty()) {
            Picasso.get()
                .load(item.urlImage)
                .into(viewBinding.imgCat)
        }

    }
}