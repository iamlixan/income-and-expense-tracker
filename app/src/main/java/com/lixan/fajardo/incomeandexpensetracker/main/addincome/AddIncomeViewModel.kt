package com.lixan.fajardo.incomeandexpensetracker.main.addincome

import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.R
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.TransactionRepository
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModel
import com.lixan.fajardo.incomeandexpensetracker.utils.ResourceManager
import com.lixan.fajardo.incomeandexpensetracker.utils.TRANSACTION_TYPE_INCOME
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class AddIncomeViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val resourceManager: ResourceManager
): BaseViewModel() {

    override fun isFirstTimeUICreated(bundle: Bundle?) = Unit

    private val _state by lazy {
        PublishSubject.create<AddIncomeState>()
    }

    val state: Observable<AddIncomeState> = _state

    fun addIncome(subType: String,
                  amount: Double,
                  notes: String,
                  dateAPI: String
    ) {
        if (!validateInput(subType, amount, dateAPI)) return

        _state.onNext(AddIncomeState.ShowLoading)
        transactionRepository.addTransaction(
            TRANSACTION_TYPE_INCOME,
            subType,
            amount,
            notes,
            dateAPI
        ).observeOn(schedulers.ui())
            .subscribeOn(schedulers.io())
            .subscribeBy(
                onSuccess = {
                    if (it.isSuccess) {
                        _state.onNext(AddIncomeState.Success(it.result()))
                    }else {
                        _state.onNext(AddIncomeState.Error(it.error().errorMessage))
                    }

                    _state.onNext(AddIncomeState.HideLoading)
                },
                onError = {
                    Timber.e(it)
                    _state.onNext(
                        AddIncomeState.Error(
                            resourceManager.getString(R.string.generic_error)
                        )
                    )

                    _state.onNext(AddIncomeState.HideLoading)
                }
            ).addTo(disposables)

    }

    private fun validateInput(
        subType: String,
        amount: Double,
        dateAPI: String
    ): Boolean {

        if (subType.isEmpty()) {
            _state.onNext(AddIncomeState.SubtypeIsEmpty)
            return false
        }

        if (amount <= 0.0) {
            _state.onNext(AddIncomeState.InvalidAmount)
            return false
        }

        if (dateAPI.isEmpty()) {
            return false
        }

        return true
    }

    fun onAmountTextChanges(text: String?) {
        if (text.isNullOrEmpty()) return
        if (text == "$") {
            _state.onNext(
                AddIncomeState.OnAmountTextClear
            )
            return
        }
        if (text.startsWith("$", false)) return
        val amountText = "$$text"
        _state.onNext(
            AddIncomeState.OnAmountTextChange(amountText)
        )
    }

}