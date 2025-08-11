package jtoir.uz.lib.domain.repository

import jtoir.uz.lib.data.model.User
import jtoir.uz.lib.data.model.tables.UserModel

interface UserRepository {
    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun insertUser(userModel: UserModel)

    fun save(user: User)
}