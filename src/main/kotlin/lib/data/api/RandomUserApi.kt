package infrastructure.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import jtoir.uz.lib.data.model.User
import kotlinx.serialization.Serializable

class RandomUserApi(private val client: HttpClient) {

    suspend fun fetchUser(): User {
        val response: RandomUserResponse = client.get("https://randomuser.me/api/?results=1").body()
        val result = response.results.first()

        return User(
            uuid = result.login.uuid,
            firstName = result.name.first,
            lastName = result.name.last,
            email = result.email,
            phone = result.phone,
            pictureUrl = result.picture.large
        )
    }
}

@Serializable
data class RandomUserResponse(
    val results: List<Result>
) {
    @Serializable
    data class Result(
        val name: Name,
        val email: String,
        val phone: String,
        val login: Login,
        val picture: Picture
    )

    @Serializable
    data class Name(val first: String, val last: String)

    @Serializable
    data class Login(val uuid: String)

    @Serializable
    data class Picture(val large: String)
}
