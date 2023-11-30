package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.PointInteret


interface JeuxListener {

    fun onInterestOpenned(pointInteret: PointInteret)

    fun onChatOpenned(chat: Chat)
    fun onClosePopUp()
}