package be.technifuture.tff.repos

import be.technifuture.tff.model.Bonus
import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.PointInteret

class ReposChat {
    private var chatForUse = mutableListOf<Chat>()

    public fun GetChats(): MutableList<Chat> {
        return chatForUse;
    }


    public fun DelChats(): MutableList<Chat> {
        return chatForUse;
    }

    companion object {
        private var instance: ReposChat? = null
        fun getInstance(): ReposChat {
            if (instance == null) {
                instance = ReposChat()
            }
            return instance as ReposChat
        }
    }

}