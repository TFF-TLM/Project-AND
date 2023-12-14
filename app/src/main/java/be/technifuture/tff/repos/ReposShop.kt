package be.technifuture.tff.repos

import be.technifuture.tff.model.*
import be.technifuture.tff.model.enums.BonusType

class ReposShop {

    public var panier = mutableListOf<PanierItemBoutique>()
    public var shop = mutableListOf<PanierItemBoutique>()

    fun PanierDel(){
        panier.clear()
    }

    fun PanierAdd(shopItem: PanierItemBoutique) {
        val existingItem = panier.find { it.bonusType == shopItem.bonusType }

        if (existingItem != null) {
            if (existingItem.prix == shopItem.prix) {
                existingItem.quantite += shopItem.quantite
                existingItem.prix = existingItem.prix * existingItem.quantite
            } else {
                panier.add(shopItem)
            }
        } else {
            panier.add(shopItem)
        }
    }

    fun mockData() {
        shop.clear()
        shop = mutableListOf(
            PanierItemBoutique(BonusType.Soins, 1.99, quantite = 1, "ico_health"),
            PanierItemBoutique(BonusType.Bouclier, 2.99, quantite = 1, "ico_shield"),
            PanierItemBoutique(BonusType.Attaque, 1.99, quantite = 1, ""),
            PanierItemBoutique(BonusType.Croquette, 0.99, quantite = 1, "ico_food"),
            PanierItemBoutique(BonusType.Chat, 4.99, quantite = 1, "ico_chat"),
            PanierItemBoutique(BonusType.Radar, 7.99, quantite = 1, "ico_radar"),
        )

    }

    companion object {

        private var instance: ReposShop? = null

        fun getInstance(): ReposShop {
            if (instance == null) {
                instance = ReposShop()
                instance!!.mockData()
            }
            return instance as ReposShop
        }
    }
}