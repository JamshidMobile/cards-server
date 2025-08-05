package jtoir.uz.lib.data.model.tables

import io.ktor.server.auth.Principal
import jtoir.uz.lib.data.model.RoleModel

data class UserModel(
    val id: Int,
    val email: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean = false,
    val role: RoleModel,
): Principal
