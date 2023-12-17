package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.Bonus

interface RadarListener {
    fun onRadarClick(action:String, item: Bonus)
}