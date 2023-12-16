package be.technifuture.tff.repos

import be.technifuture.tff.model.PanierItemBoutique
import be.technifuture.tff.model.enums.BonusType

class ReposShop {

    var panier = mutableListOf<PanierItemBoutique>()
    var shop = mutableListOf<PanierItemBoutique>()


    fun panierAddFromShop(item: PanierItemBoutique){
        val existingItem = panier.find { it.bonusType == item.bonusType }
        if (existingItem != null) {
            if (existingItem.prix == item.prix) {
                existingItem.quantite ++
            } else {
                panier.add(item)
            }
        } else {
            panier.add(item)
        }
    }

    fun getTotal(): String{
        var prix = 0.0
        panier.forEach{
            prix += (it.prix * it.quantite)
        }
        return String.format("%.2f", prix)
    }

    fun panierRemove(item: PanierItemBoutique){
        val existingItem = panier.find { it.bonusType == item.bonusType }
        existingItem?.quantite = existingItem?.quantite!! - 1
    }

    fun panierDel(item: PanierItemBoutique){
        panier.remove(item)
    }

    fun panierAdd(item: PanierItemBoutique) {
        val existingItem = panier.find { it.bonusType == item.bonusType }
        existingItem?.quantite = existingItem?.quantite!! + 1
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