package jtoir.uz

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jtoir.uz.lib.authentification.JwtService
import jtoir.uz.lib.data.model.RoleModel
import jtoir.uz.lib.data.model.tables.UserModel
import jtoir.uz.lib.data.repository.UserRepositoryImpl
import jtoir.uz.lib.domain.usecase.UserUseCase
import jtoir.uz.lib.utils.Constants
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.slf4j.event.*

fun Application.configureSecurity() {
    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

//    runBlocking {
//        userUseCase.createUser(
//            UserModel(
//                id = 1,
//                email = "j.toirjonov@ung.uz",
//                login = "jtoir",
//                password = "1111",
//                firstName = "Jamshid",
//                lastName = "Toirjonov",
//                role = RoleModel.MODERATOR,
//                isActive = true
//            )
//        )
//    }

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm ="Service server"
            validate { credential ->
                val payload = credential.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email)
                user
            }
        }
    }
}
