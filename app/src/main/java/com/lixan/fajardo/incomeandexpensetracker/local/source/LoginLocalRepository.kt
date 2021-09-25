package com.lixan.fajardo.incomeandexpensetracker.local.source

import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken
import io.reactivex.Completable
import io.reactivex.Single

interface LoginLocalRepository {

    fun saveUserDetails(user: UserDataToken): Single<UserDataToken>

    fun getUserDetails(): Single<UserDataToken>

    fun logoutUser(): Completable
}