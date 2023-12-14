package be.technifuture.tff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.technifuture.tff.R
import be.technifuture.tff.model.PanierItemBoutique
import be.technifuture.tff.model.interfaces.BoutiqueListener

class PanierAdapter(
    private val PanierItemsListe: MutableList<PanierItemBoutique>,
    private val onClickListener: BoutiqueListener
) : RecyclerView.Adapter<PanierViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PanierViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cell_panier_item, parent, false)
        return PanierViewHolder(layout, onClickListener)
    }

    override fun onBindViewHolder(holder: PanierViewHolder, position: Int) {
        val item: PanierItemBoutique = PanierItemsListe[position]
        holder.setupData(item)
    }

    override fun getItemCount(): Int {
        return PanierItemsListe.size
    }
}

class PanierViewHolder(
    private val view: View,
    private val onClickListener: BoutiqueListener

) : RecyclerView.ViewHolder(view) {

    val info: TextView = view.findViewById(R.id.CellPanierName)
    val nombre: TextView = view.findViewById(R.id.CellPanierItemNB)
    val btnValidate: ImageButton = view.findViewById(R.id.CellPanierBtnAdd)
    val btnDelete: ImageButton = view.findViewById(R.id.CellPanierBtnDel)

    fun setupData(item: PanierItemBoutique) {
        info.text = item.bonusType.toString() + "\n" + "prix : " + (item.prix * nombre.text.toString().toInt()).toString()
        nombre.text = "1"

        btnValidate.setOnClickListener {
            onClickListener.onBonusClick("VALIDATE_PANIER", item)
            item.quantite = nombre.text.toString().toInt()
        }

        btnDelete.setOnClickListener {
            onClickListener.onBonusClick("DEL_PANIER", item)
        }
    }
}

