package com.lixan.fajardo.incomeandexpensetracker.main.login

import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken

sealed class LoginState {

    data class LoginSuccess(val userData: UserDataToken) : LoginState()

    object EmailIsEmpty : LoginState()

    object EmailIsInvalid : LoginState()

    object PasswordIsEmpty : LoginState()

    object IsLoading : LoginState()

    object IsFinishedLoading : LoginState()

    data class Error(val errorMessage: String) : LoginState()
}