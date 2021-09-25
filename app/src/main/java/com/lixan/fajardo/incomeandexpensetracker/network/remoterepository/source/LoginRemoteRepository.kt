package com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source

import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import io.reactivex.Single

interface LoginRemoteRepository {

    fun doLogin(email: String, password: String): Single<RequestResult<UserDataToken>>
}