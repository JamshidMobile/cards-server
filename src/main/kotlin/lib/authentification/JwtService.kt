package jtoir.uz.lib.authentification

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import jtoir.uz.lib.data.model.tables.UserModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import io.github.cdimascio.dotenv.dotenv
val dotenv = dotenv {
    ignoreIfMalformed = true
    ignoreIfMissing = true
}


class JwtService {
    private val issuer = "my_test_cards_server"
    private val jwtSecret = dotenv["JWT_SECRET"] ?: System.getenv("JWT_SECRET") ?: "test_secret"
    private val algorithm = Algorithm.HMAC256(jwtSecret)

    private val verifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: UserModel): String {
        return JWT.create()
            .withSubject("CardsAppAuthentification")
            .withIssuer(issuer)
            .withClaim("email",user.email)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
            .sign(algorithm)
    }

    fun getVerifier(): JWTVerifier = verifier
}