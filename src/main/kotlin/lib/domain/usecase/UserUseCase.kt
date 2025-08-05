package jtoir.uz.lib.domain.usecase

import com.auth0.jwt.JWTVerifier
import jtoir.uz.lib.authentification.JwtService
import jtoir.uz.lib.data.model.tables.UserModel
import jtoir.uz.lib.domain.repository.UserRepository
import org.h2.engine.User

class UserUseCase(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    suspend fun createUser(userModel: UserModel) = userRepository.insertUser(userModel)

    suspend fun findUserByEmail(email: String) = userRepository.getUserByEmail(email)

    fun generateToken(userModel: UserModel) = jwtService.generateToken(userModel)

    fun getJwtVerifier(): JWTVerifier = jwtService.getVerifier()
}