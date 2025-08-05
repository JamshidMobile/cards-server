package jtoir.uz.lib.data.repository

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import jtoir.uz.DatabaseFactory.dbQuery
import jtoir.uz.lib.data.model.getRoleByString
import jtoir.uz.lib.data.model.getStringByRole
import jtoir.uz.lib.data.model.tables.UserModel
import jtoir.uz.lib.data.model.tables.UserTable
import jtoir.uz.lib.domain.repository.UserRepository
import org.jetbrains.exposed.sql.selectAll


class UserRepositoryImpl : UserRepository {
    override suspend fun getUserByEmail(email: String): UserModel? {
        return dbQuery {
            UserTable
                .selectAll()
                .where { UserTable.email eq email }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    override suspend fun insertUser(userModel: UserModel) {
        return dbQuery {
            UserTable.insert { table ->
                table[email] = userModel.email
                table[login] = userModel.login
                table[password] = userModel.password
                table[firstName] = userModel.firstName
                table[lastName] = userModel.lastName
                table[isActive] = userModel.isActive
                table[role] = userModel.role.getStringByRole()
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if(row == null) {
            return null
        }

        return UserModel(
            id = row[UserTable.id],
            email = row[UserTable.email],
            password = row[UserTable.password],
            login = row[UserTable.login],
            isActive = row[UserTable.isActive],
            lastName = row[UserTable.lastName],
            firstName = row[UserTable.firstName],
            role = row[UserTable.role].getRoleByString()
        )
    }
}