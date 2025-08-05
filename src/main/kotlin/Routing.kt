package jtoir.uz

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jtoir.uz.lib.domain.usecase.CardUseCase
import jtoir.uz.lib.domain.usecase.UserUseCase
import jtoir.uz.lib.routes.CardsRoute
import jtoir.uz.lib.routes.UserRoute

fun Application.configureRouting(userUseCase: UserUseCase, cardUseCase: CardUseCase) {
   routing {
       UserRoute(userUseCase)
       CardsRoute(cardUseCase)
   }
}
