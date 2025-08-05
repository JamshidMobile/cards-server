package jtoir.uz.lib.domain.repository

import jtoir.uz.lib.data.model.CardModel

interface CardRepository {

    suspend fun addCard(card:CardModel)

    suspend fun getAllCards(): List<CardModel>

    suspend fun updateCard(card: CardModel,ownerId: Int)

    suspend fun deleteCard(cardId : Int, ownerId: Int)

}