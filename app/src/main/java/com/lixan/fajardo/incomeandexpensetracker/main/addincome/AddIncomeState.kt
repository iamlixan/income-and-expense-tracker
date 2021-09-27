package com.lixan.fajardo.incomeandexpensetracker.main.addincome

import com.lixan.fajardo.incomeandexpensetracker.data.models.Transaction

sealed class AddIncomeState {
    data class Success(val transaction: Transaction): AddIncomeState()

    data class Error(val errorMessage: String): AddIncomeState()

    data class OnAmountTextChange(val text: String) : AddIncomeState()

    object OnAmountTextClear: AddIncomeState()

    object ShowLoading: AddIncomeState()

    object HideLoading: AddIncomeState()

    object InvalidAmount : AddIncomeState()

    object SubtypeIsEmpty : AddIncomeState()

}