package be.technifuture.tff.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.databinding.CellCreateClanBinding
import be.technifuture.tff.model.ClanModel

class CreateClanAdapter(private var list: MutableList<ClanModel>, private var onClick: (Int) -> Unit) :

    RecyclerView.Adapter<CreateClanViewHolder>() {
    private lateinit var binding: CellCreateClanBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreateClanViewHolder {
        binding = CellCreateClanBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CreateClanViewHolder(binding, onClick)
        }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CreateClanViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

class CreateClanViewHolder(private var viewBinding: CellCreateClanBinding, private var onClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private var selectedPos: Int = -1

    fun bind(item: ClanModel) {
        viewBinding.cardView.setOnClickListener{
            onClick(item.id)
            selectedPos = item.id
        }
        viewBinding.imageClan.setImageResource(
            when(item.id){
                1 -> R.drawable.clan_jaune
                2 -> R.drawable.clan_rouge
                3 -> R.drawable.clan_vert
                else -> R.drawable.clan_jaune
            }
        )
        viewBinding.descClan.text = item.description
        viewBinding.nameClan.text = item.name
        if(selectedPos == item.id){
            viewBinding.cardView.setCardBackgroundColor(Color.RED)
        }
    }
}

