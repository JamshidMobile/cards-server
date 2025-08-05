package jtoir.uz.lib.domain.repository

import jtoir.uz.lib.data.model.tables.UserModel
import org.h2.engine.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun insertUser(userModel: UserModel)
}