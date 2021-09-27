package com.lixan.fajardo.incomeandexpensetracker.data.repository.implementation

import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.LoginRepository
import com.lixan.fajardo.incomeandexpensetracker.local.source.LoginLocalRepository
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source.LoginRemoteRepository
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    val remote: LoginRemoteRepository,
    val local : LoginLocalRepository
): LoginRepository {

    override fun saveUserDetails(userDetails: UserDataToken): Single<UserDataToken> {
        return local.saveUserDetails(userDetails)
    }

    override fun login(email: String, password: String): Single<RequestResult<UserDataToken>> {
        return remote.doLogin(email, password)
            .flatMap { requestResult ->
                if (requestResult.isSuccess) {
                    saveUserDetails(requestResult.result())
                        .toObservable()
                        .singleOrError()
                        .flatMap {
                            Single.just(requestResult)
                        }
                } else {
                    Single.just(requestResult)
                }
            }
    }

    override fun getUserDetails(): Single<UserDataToken> {
        return local.getUserDetails()
    }

    override fun logoutUser(): Completable {
        return local.logoutUser()
    }

}