package com.lixan.fajardo.incomeandexpensetracker.local.implementation

import androidx.room.EmptyResultSetException
import androidx.room.Transaction
import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken
import com.lixan.fajardo.incomeandexpensetracker.local.database.AppDatabase
import com.lixan.fajardo.incomeandexpensetracker.local.models.DBUserDataToken
import com.lixan.fajardo.incomeandexpensetracker.local.source.LoginLocalRepository
import com.lixan.fajardo.incomeandexpensetracker.utils.OnErrorResumeNext
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginLocalRepositoryImpl @Inject constructor(
    val database: AppDatabase
): LoginLocalRepository {

    @Transaction
    override fun saveUserDetails(user: UserDataToken): Single<UserDataToken> {
        return Single.create { singleEmitter ->
            val dbUserDataToken = DBUserDataToken.fromDomain(user)
            database.userDataDao()
                .logoutUser()
            database.userDataDao()
                .insertOrUpdate(dbUserDataToken)

            singleEmitter.onSuccess(user)
        }
    }

    override fun getUserDetails(): Single<UserDataToken> {
        return database.userDataDao()
            .getUserDataToken()
            .compose (
                OnErrorResumeNext<DBUserDataToken, EmptyResultSetException>(
                    DBUserDataToken.empty(),
                    EmptyResultSetException::class.java
                )
            )
            .map {
                DBUserDataToken.toDomain(it)
            }
    }

    @Transaction
    override fun logoutUser(): Completable {
        return Completable.create{
            database.userDataDao().logoutUser()
            it.onComplete()
        }
    }

}