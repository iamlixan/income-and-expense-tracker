package com.lixan.fajardo.incomeandexpensetracker.main.addexpense

import com.lixan.fajardo.incomeandexpensetracker.data.models.Transaction

sealed class AddExpenseState {

    data class Success(val transaction: Transaction): AddExpenseState()

    data class Error(val errorMessage: String): AddExpenseState()

    object ShowLoading: AddExpenseState()

    object HideLoading: AddExpenseState()

    object InvalidAmount : AddExpenseState()

}