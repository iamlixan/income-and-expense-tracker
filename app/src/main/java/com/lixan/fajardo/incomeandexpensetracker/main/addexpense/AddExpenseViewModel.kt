package com.lixan.fajardo.incomeandexpensetracker.main.addexpense

import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.TransactionRepository
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModel
import com.lixan.fajardo.incomeandexpensetracker.main.login.LoginState
import com.lixan.fajardo.incomeandexpensetracker.utils.ResourceManager
import com.lixan.fajardo.incomeandexpensetracker.utils.TRANSACTION_TYPE_EXPENSE
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class AddExpenseViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val resourceManager: ResourceManager
): BaseViewModel() {

    override fun isFirstTimeUICreated(bundle: Bundle?) = Unit

    private val _state by lazy {
        PublishSubject.create<AddExpenseState>()
    }

    val state : Observable<AddExpenseState> = _state

    fun addExpense(
        subType: String,
        amount: Double,
        notes: String,
        dateAPI: String
    ) {

        if (!validateInput(subType, amount, dateAPI)) return

        _state.onNext(AddExpenseState.ShowLoading)
        transactionRepository
            .addTransaction(
                TRANSACTION_TYPE_EXPENSE,
                subType,
                amount,
                notes,
                dateAPI
            )
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribeBy(
                onSuccess = {
                    if (it.isSuccess) {
                        _state.onNext(
                            AddExpenseState.Success(it.result())
                        )
                    } else {
                        _state.onNext(
                            AddExpenseState.Error(it.error().errorMessage)
                        )
                    }

                    _state.onNext(AddExpenseState.HideLoading)
                },
                onError = {
                    Timber.e(it)
                    _state.onNext(
                        AddExpenseState.HideLoading
                    )
                    _state.onNext(
                        AddExpenseState.Error(resourceManager.getString(R.string.generic_error))
                    )
                }
            )
            .addTo(disposables)
    }

    private fun validateInput(
        subType: String,
        amount: Double,
        dateAPI: String
    ): Boolean {

        if (subType.isEmpty()) {
            _state.onNext(AddExpenseState.SubtypeIsEmpty)
            return false
        }

        if (amount <= 0.0) {
            _state.onNext(AddExpenseState.InvalidAmount)
            return false
        }

        if (dateAPI.isEmpty()) {
            return false
        }

        return true
    }

}