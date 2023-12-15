package be.technifuture.tff.repos

import android.util.Log
import be.technifuture.tff.model.*
import be.technifuture.tff.model.enums.BonusType

class ReposShop {

    public var panier = mutableListOf<PanierItemBoutique>()
    public var shop = mutableListOf<PanierItemBoutique>()


    fun PanierAddFromShop(item: PanierItemBoutique){
        val existingItem = panier.find { it.bonusType == item.bonusType }
        if (existingItem != null) {
            if (existingItem.prix == item.prix) {
                panier.remove(existingItem)
                existingItem.quantite = item.quantite+1
                panier.add(existingItem)
            } else {
                panier.add(item)
            }
        } else {
            panier.add(item)
        }
    }

    fun getTotal(): String{
        var Prix: Double = 0.0
        panier.forEach{it ->
            Prix += (it.prix * it.quantite)
        }
        return String.format("%.2f", Prix.toDouble())
    }

    fun PanierRemove(item: PanierItemBoutique){
        val existingItem = panier.find { it.bonusType == item.bonusType }
        panier.remove(existingItem)
        item.quantite --
        panier.add(item)
    }

    fun PanierDel(item: PanierItemBoutique){
        panier.remove(item)
    }

    fun PanierAdd(item: PanierItemBoutique) {
        val existingItem = panier.find { it.bonusType == item.bonusType }
        panier.remove(existingItem)
        item.quantite ++
        panier.add(item)
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