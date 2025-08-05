package jtoir.uz.lib.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val login: String,
    val firstname: String,
    val lastname: String,
    val isActive: Boolean = false,
    val role: String
)
