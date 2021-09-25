package com.lixan.fajardo.incomeandexpensetracker.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lixan.fajardo.incomeandexpensetracker.data.models.User
import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken


@Entity(tableName = DBUserDataToken.TABLE_NAME)
data class DBUserDataToken(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = -1,
    @ColumnInfo(name = "token") val token: String,
    @ColumnInfo(name = "user") val user: User

) {
    companion object {
        const val TABLE_NAME = "DBUserDataToken"

        fun empty() : DBUserDataToken {
            return DBUserDataToken(
                token = "",
                user = User.empty()
            )
        }

        fun fromDomain(userDataToken: UserDataToken): DBUserDataToken {
            return with(userDataToken) {
                DBUserDataToken(
                    token = userDataToken.token,
                    user = User(
                        firstName = userDataToken.user.firstName,
                        createdOn = userDataToken.user.createdOn,
                        email = userDataToken.user.email,
                        lastName = userDataToken.user.lastName,
                        id = userDataToken.user.id
                    )
                )
            }
        }

        fun toDomain(userDataTokenDB: DBUserDataToken): UserDataToken {
            return with(userDataTokenDB) {
                UserDataToken(
                    token = userDataTokenDB.token,
                    user = User(
                        firstName = userDataTokenDB.user.firstName,
                        createdOn = userDataTokenDB.user.createdOn,
                        email = userDataTokenDB.user.email,
                        lastName = userDataTokenDB.user.lastName,
                        id = userDataTokenDB.user.id
                    )
                )
            }
        }
    }
}