package be.technifuture.tff.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.databinding.CellCreateAvatarBinding

class CreateAvatarAdapter(private var choose: MutableList<String>, private val onClick: (String) -> Unit) :

    RecyclerView.Adapter<CreateAvatarViewHolder>() {
    private lateinit var binding: CellCreateAvatarBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreateAvatarViewHolder {
        //charge le layout de la cellule
        binding = CellCreateAvatarBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CreateAvatarViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CreateAvatarViewHolder, position: Int) {
        holder.bind(choose[position])
    }


    //retourne le nbr d'élément à afficher
    override fun getItemCount(): Int {
        return choose.size
    }
}

class CreateAvatarViewHolder(private var viewBinding: CellCreateAvatarBinding, val onClick: (String) -> Unit) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: String) {

        viewBinding.label.text = item

        viewBinding.cardView.setOnClickListener{
            onClick(item)
        }

    }
}

