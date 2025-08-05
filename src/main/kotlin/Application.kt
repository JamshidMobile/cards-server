package jtoir.uz

import io.ktor.server.application.*
import jtoir.uz.DatabaseFactory.initializationDatabase

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    initializationDatabase()
//    configureSecurity()
//    configureMonitoring()
//    configureSerialization()
//    configureRouting()
}
