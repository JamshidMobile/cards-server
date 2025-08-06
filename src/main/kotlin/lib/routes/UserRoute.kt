package jtoir.uz.lib.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.receiveNullable
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import jtoir.uz.lib.authentification.hash
import jtoir.uz.lib.data.model.getRoleByString
import jtoir.uz.lib.data.model.requests.LoginRequest
import jtoir.uz.lib.data.model.requests.RegisterRequest
import jtoir.uz.lib.data.model.response.BaseResponse
import jtoir.uz.lib.data.model.tables.UserModel
import jtoir.uz.lib.domain.usecase.UserUseCase
import jtoir.uz.lib.utils.Constants
import java.util.Locale
import java.util.Locale.getDefault

fun Route.UserRoute(userUseCase: UserUseCase) {

    val hashFunction = { s: String -> hash(s) }

     post("api/v1/signup") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }
        try {
            val user  = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(getDefault()),
                login = registerRequest.login.trim().lowercase(getDefault()),
                password = hashFunction(registerRequest.password),
                firstName = registerRequest.firstname.trim(),
                lastName = registerRequest.lastname.trim(),
                role = registerRequest.role.trim().getRoleByString()
            )
            userUseCase.createUser(user)
            call.respond(HttpStatusCode.OK,BaseResponse(true, userUseCase.generateToken(user)))
        }catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, message = BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    post("api/v1/login") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val user = userUseCase.findUserByEmail(loginRequest.email.trim().lowercase(getDefault()))
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.WRONG_EMAIL))
            }else{
                if(user.password == hashFunction(loginRequest.password)) {
                    call.respond(status = HttpStatusCode.OK, message = BaseResponse(success = true, message = userUseCase.generateToken(user)))
                }else{
                    call.respond(status = HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.INCORRECT_PASSWORD))
                }
            }

        }
        catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, message = BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    authenticate("jwt") {
        get("api/v1/get-user-info") {
            try {
                val user = call.principal<UserModel>()
                if(user != null){
                    call.respond(status = HttpStatusCode.OK, message = user)
                }else{
                    call.respond(status = HttpStatusCode.BadRequest, message = BaseResponse(false, Constants.Error.USER_NOT_FOUND))
                }
            }catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, message = BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }
    }
}