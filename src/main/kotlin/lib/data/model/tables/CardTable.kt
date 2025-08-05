package jtoir.uz.lib.data.model.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CardTable: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val owner: Column<Int> = integer("card_owner").references(UserTable.id)
    val cardTitle: Column<String> = varchar("card_title", 50)
    val cardDescription: Column<String> = varchar("card_description", 2000)
    val cardCreatedDate: Column<String> = varchar("card_created_date", 50)
    val isVerified: Column<Boolean> = bool("is_verified")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}