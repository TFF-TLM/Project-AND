package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.Bonus

interface BonusListener {
    fun onBonusClick(action:String, item: Bonus)
}