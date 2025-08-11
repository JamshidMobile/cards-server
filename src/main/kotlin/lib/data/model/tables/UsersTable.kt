package jtoir.uz.lib.data.model.tables

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val uuid = varchar("uuid", 50)
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 100)
    val phone = varchar("phone", 20)
    val pictureUrl = varchar("picture_url", 200)
}