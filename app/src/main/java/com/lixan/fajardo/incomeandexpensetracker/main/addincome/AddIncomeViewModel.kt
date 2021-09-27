package com.lixan.fajardo.incomeandexpensetracker.main.addincome

import android.os.Bundle
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.TransactionRepository
import com.lixan.fajardo.incomeandexpensetracker.di.base.BaseViewModel
import com.lixan.fajardo.incomeandexpensetracker.utils.ResourceManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AddIncomeViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val resourceManager: ResourceManager
): BaseViewModel() {

    override fun isFirstTimeUICreated(bundle: Bundle?) = Unit

    private val _state by lazy {
        PublishSubject.create<AddIncomeState>()
    }

    private val state: Observable<AddIncomeState> = _state

}