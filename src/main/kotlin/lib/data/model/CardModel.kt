package jtoir.uz.lib.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CardModel(
    val id : Int,
    val ownerId : Int,
    val cardTitle : String,
    val cardDescription : String,
    val cardDate : String,
    val isVerified : Boolean = false
)
