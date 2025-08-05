package jtoir.uz

import io.ktor.server.application.*
import jtoir.uz.DatabaseFactory.initializationDatabase
import jtoir.uz.lib.authentification.JwtService
import jtoir.uz.lib.data.repository.CardRepositoryImpl
import jtoir.uz.lib.data.repository.UserRepositoryImpl
import jtoir.uz.lib.domain.usecase.CardUseCase
import jtoir.uz.lib.domain.usecase.UserUseCase

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val cardRepository = CardRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val cardUseCase = CardUseCase(cardRepository)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
//    configureRouting()
}
