package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.PointInteret
import be.technifuture.tff.model.enums.ChoixPopUp


interface JeuxListener {

    fun onInterestOpenned(id: String)
    fun onChatOpenned(id: String)
    fun onClosePopUp()
}