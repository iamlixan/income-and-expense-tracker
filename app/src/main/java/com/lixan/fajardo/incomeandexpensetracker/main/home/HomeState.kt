package com.lixan.fajardo.incomeandexpensetracker.main.home

import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken

sealed class HomeState {

    data class GetUserDetailsSuccess(val userDetails: UserDataToken): HomeState()

    data class Error(val errorMessage: String): HomeState()

    object LogoutUser : HomeState()

}