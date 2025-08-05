package jtoir.uz.lib.data.repository

import jtoir.uz.DatabaseFactory.dbQuery
import jtoir.uz.lib.data.model.CardModel
import jtoir.uz.lib.data.model.tables.CardTable
import jtoir.uz.lib.domain.repository.CardRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class CardRepositoryImpl: CardRepository {
    override suspend fun addCard(card: CardModel) {
        dbQuery {
            CardTable.insert { table ->
                table[owner] = card.ownerId
                table[cardTitle] = card.cardTitle
                table[cardDescription] = card.cardDescription
                table[cardCreatedDate] = card.cardDate
                table[isVerified] = card.isVerified
            }
        }
    }

    override suspend fun getAllCards(): List<CardModel> {
        return dbQuery {
            CardTable.selectAll()
                .mapNotNull {
                    rowToCard(it)
                }
        }
    }

    override suspend fun updateCard(card: CardModel,ownerId: Int) {
        dbQuery {
            CardTable.update({ CardTable.owner.eq(ownerId) and CardTable.id.eq(card.id) }) {
                it[owner] = card.ownerId
                it[cardTitle] = card.cardTitle
                it[cardDescription] = card.cardDescription
                it[cardCreatedDate] = card.cardDate
                it[isVerified] = card.isVerified
            }
        }
    }

    override suspend fun deleteCard(cardId: Int, ownerId: Int) {
        dbQuery {
            CardTable.deleteWhere { CardTable.id.eq( cardId) and CardTable.owner.eq(ownerId) }
        }
    }

    private fun rowToCard(row: ResultRow?): CardModel? {
        if (row == null) {
            return null
        }
        return CardModel(
            row[CardTable.id],
            row[CardTable.owner],
            row[CardTable.cardTitle],
            row[CardTable.cardDescription],
            row[CardTable.cardCreatedDate],
            row[CardTable.isVerified],
        )
    }
}