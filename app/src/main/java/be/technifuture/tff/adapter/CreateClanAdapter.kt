package be.technifuture.tff.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.databinding.CellCreateClanBinding
import be.technifuture.tff.model.ClanModel

class CreateClanAdapter(private var choose: MutableList<ClanModel>, private var onClick: (Int) -> Unit) :

    RecyclerView.Adapter<CreateClanViewHolder>() {
    private lateinit var binding: CellCreateClanBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreateClanViewHolder {
        //charge le layout de la cellule
        binding = CellCreateClanBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CreateClanViewHolder(binding, onClick)
        }

    override fun getItemCount(): Int {
        return choose.size
    }

    override fun onBindViewHolder(holder: CreateClanViewHolder, position: Int) {
        holder.bind(choose[position])
    }
}

class CreateClanViewHolder(private var viewBinding: CellCreateClanBinding, private var onClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: ClanModel) {
        viewBinding.cardView.setOnClickListener{
            onClick(item.id)
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
    }
}

