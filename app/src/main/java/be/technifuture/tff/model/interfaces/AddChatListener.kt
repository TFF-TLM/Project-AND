package be.technifuture.tff.model.interfaces

import be.technifuture.tff.model.Chat

interface AddChatListener {
    fun onAddChatClick(action:String, item: Chat)
}