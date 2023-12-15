package be.technifuture.tff.service.network.dto

import be.technifuture.tff.model.Chat
import be.technifuture.tff.model.ZoneChat
import be.technifuture.tff.service.network.manager.AuthDataManager
import com.google.gson.annotations.SerializedName

data class CatWithInteract(
    val id: Int,
    val owner: UserInfoResponse,
    val clan: ClanResponse,
    val name: String,
    val lvl: Int,
    val exp: Int,
    @SerializedName("limite_exp")
    val limiteExp: Int,
    val timestamp: String,
    val image: String,
    val origin: Position?,
    val position: Position?,
    val alive: Boolean,
    val radius: Int,
    val interact: List<InteractCat>
) {
    fun toZoneChat(): ZoneChat {
        return ZoneChat(
            this.id.toString(),
            this.name,
            this.radius,
            this.clan.toChatRGB(),
            this.origin?.toGpsCoordinates(),
            this.toChat()
        )
    }

    fun toChat(): Chat {
       return Chat(
           this.id.toString(),
           this.image,
           this.name,
           this.exp,
           this.limiteExp,
           this.lvl,
           false,
           this.position?.toGpsCoordinates(),
           0,
           this.clan.toClanModel(),
           this.owner.username,
           this.alive,
           recievedFood()
       )
    }

    private fun recievedFood(): Int {
        return if(interact.isNotEmpty()) {
            interact.first().givenFood
        } else {
            0
        }
    }
}

data class CatWithAllInteract(
    val id: Int,
    val owner: UserInfoResponse,
    val clan: ClanResponse,
    val name: String,
    val lvl: Int,
    val exp: Int,
    @SerializedName("limite_exp")
    val limiteExp: Int,
    val timestamp: String,
    val image: String,
    val origin: Position?,
    val position: Position?,
    val alive: Boolean,
    val radius: Int,
    val interact: List<InteractCatWithUser>
) {
    fun toZoneChat(): ZoneChat {
        return ZoneChat(
            this.id.toString(),
            this.name,
            this.radius,
            this.clan.toChatRGB(),
            this.origin?.toGpsCoordinates(),
            this.toChat()
        )
    }

    fun toChat(): Chat {
        return Chat(
            this.id.toString(),
            this.image,
            this.name,
            this.exp,
            this.limiteExp,
            this.lvl,
            false,
            this.position?.toGpsCoordinates(),
            0,
            this.clan.toClanModel(),
            this.owner.username,
            this.alive,
            recievedFood()
        )
    }

    private fun recievedFood(): Int {
        val currentUserInteract = interact.filter { it.user.id == AuthDataManager.instance.user.id }
        return if(currentUserInteract.isNotEmpty()) {
            currentUserInteract.first().givenFood
        } else {
            0
        }
    }
}

data class Cat(
    val id: Int,
    val owner: UserInfoResponse,
    val clan: ClanResponse,
    val name: String,
    val lvl: Int,
    val exp: Int,
    @SerializedName("limite_exp")
    val limiteExp: Int,
    val timestamp: String,
    val image: String,
    val origin: Position?,
    val position: Position?,
    val alive: Boolean,
    val radius: Int
) {
    fun toZoneChat(): ZoneChat {
        return ZoneChat(
            this.id.toString(),
            this.name,
            this.radius,
            this.clan.toChatRGB(),
            this.origin?.toGpsCoordinates(),
            this.toChat()
        )
    }

    fun toChat(): Chat {
        return Chat(
            this.id.toString(),
            this.image,
            this.name,
            this.exp,
            this.limiteExp,
            this.lvl,
            false,
            this.position?.toGpsCoordinates(),
            0,
            this.clan.toClanModel(),
            this.owner.username,
            this.alive,
            0
        )
    }
}

data class CatInBagResponse(
    val cats: List<Cat>
) {
    fun toZoneChatList(): List<ZoneChat> {
        val list = mutableListOf<ZoneChat>()
        cats.forEach {
            list.add(it.toZoneChat())
        }
        return list
    }
}

data class CatOnMapResponse(
    val cats: List<CatWithAllInteract>
) {
    fun toZoneChatList(): List<ZoneChat> {
        val list = mutableListOf<ZoneChat>()
        cats.forEach {
            list.add(it.toZoneChat())
        }
        return list
    }
}

data class DropCatRequestBody(
    @SerializedName("cat_id")
    val catId: Int,
    val name: String,
    val latitude: Float,
    val longitude: Float
)

data class DropCatResponse(
    val cat: Cat
)
