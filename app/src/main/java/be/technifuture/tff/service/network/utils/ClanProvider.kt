package be.technifuture.tff.service.network.utils

import be.technifuture.tff.R
import be.technifuture.tff.model.ClanModel

object ClanBuilder {
    private val listOfClan = mapOf(
        1 to Pair(R.drawable.clan_jaune, "Bonus à l'expansion"),
        2 to Pair(R.drawable.clan_rouge, "Bonus à l'attaque"),
        3 to Pair(R.drawable.clan_vert, "Résistance aux attaques")
    )

    fun buildClan(id: Int, name: String): ClanModel {
        val item = listOfClan[id]
        return ClanModel(
            id,
            name.replaceFirstChar(Char::titlecase),
            item?.first ?: 0,
            item?.second ?: ""
        )
    }
}