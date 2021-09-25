package com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.implementation

import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken
import com.lixan.fajardo.incomeandexpensetracker.network.ApiService
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source.LoginRemoteRepository
import com.lixan.fajardo.incomeandexpensetracker.network.response.ErrorHandler
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import com.lixan.fajardo.incomeandexpensetracker.network.response.ResultError
import com.lixan.fajardo.incomeandexpensetracker.network.response.dto.UserDataTokenDTO
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class LoginRemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRemoteRepository {

    override fun doLogin(email: String, password: String): Single<RequestResult<UserDataToken>> {
        val userMap = hashMapOf<String, String>()
        userMap["email"] = email
        userMap["password"] = password

        return apiService.login(userMap)
            .map { response ->
                if (response.token.isNotEmpty()) {
                    RequestResult.success(
                        UserDataTokenDTO.mapUserDataToken(response)
                    )
                } else {
                    RequestResult.error(
                        ResultError(response.message.orEmpty())
                    )
                }
            }
            .onErrorReturn {
                RequestResult.error(
                    ErrorHandler.handleError(it)
                )
            }
    }

}