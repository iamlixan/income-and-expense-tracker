package com.lixan.fajardo.incomeandexpensetracker.main.home

import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.LoginRepository
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModel
import com.lixan.fajardo.incomeandexpensetracker.utils.ResourceManager
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resourceManager: ResourceManager
): BaseViewModel() {


    private val _state by lazy {
        PublishSubject.create<HomeState>()
    }

    val state : Observable<HomeState> = _state

    override fun isFirstTimeUICreated(bundle: Bundle?) {
        loginRepository.getUserDetails()
            .observeOn(schedulers.ui())
            .subscribeOn(schedulers.io())
            .subscribeBy(
                onSuccess = {
                    _state.onNext(
                        HomeState.GetUserDetailsSuccess(it)
                    )
                },
                onError = {
                    _state.onNext(
                        HomeState.Error(resourceManager.getString(R.string.error_user_session_expired))
                    )

                    logoutUser()
                }
            ).addTo(disposables)
    }

    fun logoutUser() {
        loginRepository.logoutUser()
            .observeOn(schedulers.ui())
            .subscribeOn(schedulers.io())
            .subscribeBy(
                onComplete = {
                    _state.onNext(
                        HomeState.LogoutUser
                    )
                },
                onError = {
                    Timber.e(it)
                }
            ).addTo(disposables)
    }

}