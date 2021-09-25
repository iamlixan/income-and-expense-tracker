package com.lixan.fajardo.incomeandexpensetracker.main.login

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

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val resourceManager: ResourceManager
) : BaseViewModel() {

    override fun isFirstTimeUICreated(bundle: Bundle?) = Unit

    private val _state by lazy {
        PublishSubject.create<LoginState>()
    }

    val state : Observable<LoginState> = _state

    fun login(email: String, password: String) {
        if (!validateInputFields(email, password)) return

        _state.onNext(
            LoginState.IsLoading
        )

        loginRepository.login(
            email,
            password
        )
            .observeOn(schedulers.ui())
            .subscribeOn(schedulers.io())
            .subscribeBy(
                onSuccess = {
                    if (it.isSuccess) {
                        _state.onNext(
                            LoginState.LoginSuccess(it.result())
                        )
                    } else {
                        if (it.error().errorMessage == resourceManager.getString(R.string.error_invalid_credentials)) {
                            _state.onNext(
                                LoginState.Error(resourceManager.getString(R.string.error_email_password_incorrect))
                            )
                        } else {
                            _state.onNext(
                                LoginState.Error(it.error().errorMessage)
                            )
                        }
                    }

                    _state.onNext(
                        LoginState.IsFinishedLoading
                    )
                },
                onError = {
                    Timber.e(it)
                    _state.onNext(
                        LoginState.Error(resourceManager.getString(R.string.generic_error))
                    )

                    _state.onNext(
                        LoginState.IsFinishedLoading
                    )
                }
            )
            .addTo(disposables)
    }

    private fun validateInputFields(email: String, password: String): Boolean {
        if (email.isBlank()) {
            _state.onNext(
                LoginState.EmailIsEmpty
            )

            return false
        }

        if (!resourceManager.validateEmail(email)) {
            _state.onNext(
                LoginState.EmailIsInvalid
            )

            return false
        }

        if (password.isBlank()) {
            _state.onNext(
                LoginState.PasswordIsEmpty
            )

            return false
        }

        return true
    }

}