package com.lixan.fajardo.incomeandexpensetracker.data.repository.source

import com.lixan.fajardo.incomeandexpensetracker.data.models.Transaction
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import io.reactivex.Single

interface TransactionRepository {

    fun addTransaction(
        type: String,
        subType: String,
        amount: Double,
        notes: String,
        date: String
    ) : Single<RequestResult<Transaction>>

}