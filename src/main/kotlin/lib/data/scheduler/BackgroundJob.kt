package jtoir.uz.lib.data.scheduler

import jtoir.uz.lib.domain.usecase.FetchAndSaveUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackgroundJob(private val useCase: FetchAndSaveUserUseCase) {
    fun start() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                try {
                    useCase.execute()
                } catch (e: Exception) {
                    println("❌ Error: ${e.message}")
                }
                delay(5 * 60 * 1000) // каждые 5 минут
            }
        }
    }
}