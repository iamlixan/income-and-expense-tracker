package com.lixan.fajardo.incomeandexpensetracker.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lixan.fajardo.incomeandexpensetracker.local.base.BaseDao
import com.lixan.fajardo.incomeandexpensetracker.local.models.DBUserDataToken
import io.reactivex.Single

@Dao
abstract class UserDataTokenDao : BaseDao<DBUserDataToken>{

    @Query("SELECT * FROM ${DBUserDataToken.TABLE_NAME}")
    abstract fun getUserDataToken(): Single<DBUserDataToken>

    @Query("DELETE FROM ${DBUserDataToken.TABLE_NAME}")
    abstract fun logoutUser()

}