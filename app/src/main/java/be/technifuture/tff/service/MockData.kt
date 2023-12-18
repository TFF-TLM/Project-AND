package be.technifuture.tff.service

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ClanModel
import be.technifuture.tff.model.GpsCoordinates
import be.technifuture.tff.model.NewUserModel
import be.technifuture.tff.model.UserModel

object MockData {

    private val chats = initMockChat()
    private val userConnected = initMockUserConnected()
    private val user = initMockUser()

    fun canConnect(mail: String, pass: String) : Boolean{
        return mail == user.mail && pass == user.password
    }

    fun getUrlAvatar() : String{
        return user.urlAvatar
    }

    fun getUserConnected(): UserModel{
        return userConnected
    }

    fun getListChat(): MutableList<Chat>{
        return chats
    }


    private fun initMockUser() : NewUserModel {
       return NewUserModel(
            "Hellana",
            "izzi.tony@tff.com",
            "T@t42o#Y",
            2,
            "https://res.cloudinary.com/dota5mahf/d0a2300c-fb85-4b91-8832-91c77d704c5d",
        )
    }

    private fun initMockUserConnected() : UserModel {
        return UserModel(
            42,
            "Hellana",
            "izzi.tony@tff.com",
            "https://res.cloudinary.com/dota5mahf/d0a2300c-fb85-4b91-8832-91c77d704c5d",
            NetworkService.clan.getClanById(2),
            5,
            150,
            55,
            72,
            99
        )
    }
    private fun initMockChat() : MutableList<Chat> {
        return mutableListOf(
            Chat(
                "0",
                "https://res.cloudinary.com/dota5mahf/4f23f2b4-31bb-4543-b5b3-0bd397c422e7",
                "Lucy",
                3,
                5,
                1,
                true,
                GpsCoordinates(0.0, 0.0),
                1,
                NetworkService.clan.getClanById(1),
                "Tony",
                true,
                40
            ),
            Chat(
                "2",
                "https://res.cloudinary.com/dota5mahf/5cadc418-82d1-47c9-904b-068be3b59073",
                "Sushi",
                5,
                10,
                2,
                true,
                GpsCoordinates(0.0, 0.0),
                1,
                NetworkService.clan.getClanById(2),
                "Medhi",
                true,
                40
            ),
            Chat(
                "3",
                "https://res.cloudinary.com/dota5mahf/b690df13-ed11-4a21-b587-e3af75c597ae",
                "Padmé",
                34,
                50,
                3,
                true,
                GpsCoordinates(0.0, 0.0),
                1,
                NetworkService.clan.getClanById(3),
                "Laurent",
                true,
                40
            ),
        )
    }
}