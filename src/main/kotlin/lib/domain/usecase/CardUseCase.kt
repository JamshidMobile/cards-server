package jtoir.uz.lib.domain.usecase

import jtoir.uz.lib.data.model.CardModel
import jtoir.uz.lib.domain.repository.CardRepository

class CardUseCase(
    private val cardRepository: CardRepository
) {
    suspend fun addCard(card: CardModel){
        cardRepository.addCard(card)
    }

    suspend fun getAllCards(): List<CardModel>{
        return cardRepository.getAllCards()
    }

    suspend fun updateCard(card: CardModel,ownerId :Int){
        cardRepository.updateCard(card, ownerId = ownerId)
    }

    suspend fun deleteCard(cardId:Int,ownerId:Int){
        cardRepository.deleteCard(cardId = cardId, ownerId = ownerId)
    }

}