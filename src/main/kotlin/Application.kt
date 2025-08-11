package jtoir.uz

import infrastructure.api.RandomUserApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import jtoir.uz.DatabaseFactory.initializationDatabase
import jtoir.uz.lib.authentification.JwtService
import jtoir.uz.lib.data.repository.CardRepositoryImpl
import jtoir.uz.lib.data.repository.UserRepositoryImpl
import jtoir.uz.lib.data.scheduler.BackgroundJob
import jtoir.uz.lib.domain.usecase.CardUseCase
import jtoir.uz.lib.domain.usecase.FetchAndSaveUserUseCase
import jtoir.uz.lib.domain.usecase.UserUseCase
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val cardRepository = CardRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val cardUseCase = CardUseCase(cardRepository)

    val client = HttpClient(CIO) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true // <- вот это
                }
            )
        }
    }

    val api = RandomUserApi(client)
    val repo = UserRepositoryImpl()
    val useCase = FetchAndSaveUserUseCase(api, repo)


    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
    configureRouting(userUseCase,cardUseCase)

    BackgroundJob(useCase).start()
}
