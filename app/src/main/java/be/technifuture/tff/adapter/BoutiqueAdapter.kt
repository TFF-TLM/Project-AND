package be.technifuture.tff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.model.PanierItemBoutique
import be.technifuture.tff.model.interfaces.BoutiqueListener
import com.bumptech.glide.Glide

class BoutiqueAdapter(
    private val ShopItemsListe: MutableList<PanierItemBoutique>,
    private val onClickListener: BoutiqueListener
) : RecyclerView.Adapter<BoutiqueViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoutiqueViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_shop_item, parent, false)
        return BoutiqueViewHolder(layout, onClickListener)
    }

    override fun onBindViewHolder(holder: BoutiqueViewHolder, position: Int) {
        val item: PanierItemBoutique = ShopItemsListe[position]
        holder.setupData(item)
    }

    override fun getItemCount(): Int {
        return ShopItemsListe.size
    }
}


class BoutiqueViewHolder(
    private val view: View,
    private val onClickListener: BoutiqueListener

) : RecyclerView.ViewHolder(view) {

    val photo: ImageView = view.findViewById(R.id.CellShopImage)
    val info: TextView = view.findViewById(R.id.CellShopName)
    val btnValidate: ImageButton = view.findViewById(R.id.CellShopBtnAdd)

    fun setupData(item: PanierItemBoutique) {
        info.text = item.bonusType.toString() + "\n" + "prix : " + item.prix.toString()
        val resourceName = "drawable/${item.urlImage}"
        val drawableResId = view.context.resources.getIdentifier(resourceName, "drawable", view.context.packageName)

        if (drawableResId != 0) {
            Glide.with(view)
                .load(drawableResId)
                .into(photo)
        }

        btnValidate.setOnClickListener {
            onClickListener.onBonusClick("ADD_TO_PANIER", item)
        }

    }
}

