package be.technifuture.tff.model

import com.google.gson.annotations.SerializedName

class ClanModel(

    val id: Int,
    val name: String,
    val image: Int,
    val description: String

   /* @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("effect_id")
    var bonus: Boolean */
)