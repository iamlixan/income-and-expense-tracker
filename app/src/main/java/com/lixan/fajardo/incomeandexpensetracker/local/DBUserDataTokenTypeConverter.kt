package com.lixan.fajardo.incomeandexpensetracker.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.lixan.fajardo.incomeandexpensetracker.data.models.User
import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken

class DBUserDataTokenTypeConverter {

    @TypeConverter
    fun userDataTokenToString(userDataToken: UserDataToken) : String {
        return Gson().toJson(userDataToken)
    }

    @TypeConverter
    fun userDataTokenFromString(userDataTokenString: String): UserDataToken {
        return Gson().fromJson(userDataTokenString, UserDataToken::class.java)
    }

    @TypeConverter
    fun userDataToString(userData: User): String {
        return Gson().toJson(userData)
    }

    @TypeConverter
    fun userDataFromString(userDataString: String): User {
        return Gson().fromJson(userDataString, User::class.java)
    }
}