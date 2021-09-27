package com.lixan.fajardo.incomeandexpensetracker.data.repository.source

import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import io.reactivex.Completable
import io.reactivex.Single

interface LoginRepository {

    fun saveUserDetails(userDetails: UserDataToken): Single<UserDataToken>

    fun login(
        email: String,
        password: String
    ) : Single<RequestResult<UserDataToken>>

    fun getUserDetails(): Single<UserDataToken>

    fun logoutUser(): Completable
}