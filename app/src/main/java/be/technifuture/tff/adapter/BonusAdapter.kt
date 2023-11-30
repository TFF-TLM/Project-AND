package be.technifuture.tff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.model.Bonus
import be.technifuture.tff.model.interfaces.BonusListener
import com.bumptech.glide.Glide

class BonusAdapter(
    private val BonusItemsListe: MutableList<Bonus>,
    private val onClickListener: BonusListener
) : RecyclerView.Adapter<BonusViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_bonus, parent, false)
        return BonusViewHolder(layout, onClickListener)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        val item: Bonus = BonusItemsListe[position]
        holder.setupData(item)
    }

    override fun getItemCount(): Int {
        return BonusItemsListe.size
    }
}


class BonusViewHolder(
    private val view: View,
    private val onClickListener: BonusListener

) : RecyclerView.ViewHolder(view) {

    val photo: ImageView = view.findViewById(R.id.cellChatBonusImage)
    val titre: TextView = view.findViewById(R.id.cellChatBonusText)

    fun setupData(item: Bonus) {
        titre.text = item.nombreItem.toString()

        if (!item.urlImage.isNullOrEmpty()) {
            Glide.with(view)
                .load(item.urlImage) // Chargez l'URL de l'image
                .into(photo) // Affichez l'image dans la vue 'photo'
        }

        view.setOnClickListener {
            onClickListener.onBonusClick("USE", item)
        }
    }
}

