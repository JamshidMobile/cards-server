package jtoir.uz.lib.domain.usecase

import jtoir.uz.lib.data.model.CardModel
import jtoir.uz.lib.data.model.response.PaginatedCardResponce
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

    suspend fun getPaginatedCards(page: Int, limit: Int): PaginatedCardResponce {
        val data = cardRepository.getPaginated(page, limit)
        val total = cardRepository.count()

        return PaginatedCardResponce(
            data = data,
            page = page,
            limit = limit,
            total = total
        )
    }

}