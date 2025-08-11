package jtoir.uz.lib.domain.usecase

import infrastructure.api.RandomUserApi
import jtoir.uz.lib.domain.repository.UserRepository

class FetchAndSaveUserUseCase (
    private val api: RandomUserApi,
    private val repo: UserRepository
) {
    suspend fun execute() {
        val user = api.fetchUser()
        repo.save(user)
        println("✅ Saved user: ${user.firstName} ${user.lastName}")
    }
}