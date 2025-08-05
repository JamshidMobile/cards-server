package jtoir.uz.lib.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import jtoir.uz.lib.data.model.CardModel
import jtoir.uz.lib.data.model.requests.AddCardRequest
import jtoir.uz.lib.data.model.response.BaseResponse
import jtoir.uz.lib.data.model.tables.UserModel
import jtoir.uz.lib.domain.usecase.CardUseCase
import jtoir.uz.lib.utils.Constants

fun Route.CardsRoute(cardUseCase: CardUseCase){


    authenticate("jwt"){

        get ("api/v1/get-all-cards") {
            try {
                val cards = cardUseCase.getAllCards()
                call.respond(HttpStatusCode.OK, cards)
            }catch (e: Exception){
                call.respond(HttpStatusCode.Conflict, message = BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/create-card") {
            val cardRequest = call.receiveNullable<AddCardRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }
            try {
                val card = CardModel(
                    id = 0,
                    ownerId = call.principal<UserModel>()!!.id,
                    cardTitle = cardRequest.cardTitle,
                    cardDescription = cardRequest.cardDescription,
                    cardDate = cardRequest.cardDate,
                    isVerified = cardRequest.isVerified,
                )
                cardUseCase.addCard(card)
                call.respond(HttpStatusCode.OK, message = BaseResponse(success = true, message = Constants.Success.CARD_ADDED_SUCCESSFULLY))
            }catch (e: Exception){
                call.respond(HttpStatusCode.Conflict, message = BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/update-card") {
            val cardRequest = call.receiveNullable<AddCardRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@post
            }
            try {
                val ownerId = call.principal<UserModel>()!!.id
                val card = CardModel(
                    id = cardRequest.id,
                    ownerId = ownerId,
                    cardTitle = cardRequest.cardTitle,
                    cardDescription = cardRequest.cardDescription,
                    cardDate = cardRequest.cardDate,
                    isVerified = cardRequest.isVerified,
                )
                cardUseCase.updateCard(card,ownerId)
                call.respond(HttpStatusCode.OK, message = BaseResponse(success = true, message = Constants.Success.CARD_CHANGED_SUCCESSFULLY))
            }catch (e: Exception){
                call.respond(HttpStatusCode.Conflict, message = BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        delete("api/v1/delete-card") {
            val cardRequest = call.receiveNullable<AddCardRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.MISSING_FIELDS))
                return@delete
            }
            try {
                val ownerId = call.principal<UserModel>()!!.id
                cardUseCase.deleteCard(cardId = cardRequest.id, ownerId)
                call.respond(HttpStatusCode.OK, message = BaseResponse(success = true, message = Constants.Success.CARD_DELETED_SUCCESSFULLY))
            }catch (e: Exception){
                call.respond(HttpStatusCode.Conflict, message = BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }
    }
}