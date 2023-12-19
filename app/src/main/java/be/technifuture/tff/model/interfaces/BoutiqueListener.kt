package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.PanierItemBoutique

interface BoutiqueListener {
    fun onBonusClick(action:String, item: PanierItemBoutique)
}