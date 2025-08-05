package jtoir.uz

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.HoconApplicationConfig
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import jtoir.uz.lib.data.model.tables.CardTable
import jtoir.uz.lib.data.model.tables.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.*

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = System.getenv("DB_POSTGRES_URL") ?: error("DB_POSTGRES_URL is not set")
    private val dbUser = System.getenv("DB_POSTGRES_USER") ?: error("DB_POSTGRES_USER is not set")
    private val dbPassword = System.getenv("DB_POSTGRES_PASSWORD") ?: error("DB_POSTGRES_PASSWORD is not set")



    fun Application.initializationDatabase(){
        Database.connect(getHikariDataSource())

        transaction {
            SchemaUtils.create(UserTable, CardTable)
        }
    }

    private fun getHikariDataSource(): HikariDataSource {
        println("DB URL : $dbUrl")
        println("DB User : $dbUser")


        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}
