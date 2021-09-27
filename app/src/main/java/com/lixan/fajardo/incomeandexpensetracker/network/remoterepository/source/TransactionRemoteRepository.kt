package com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source

import com.lixan.fajardo.incomeandexpensetracker.data.models.Transaction
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import io.reactivex.Single

interface TransactionRemoteRepository {

    fun addTransaction(
        token: String,
        type: String,
        subType: String,
        amount: Double,
        notes: String,
        date: String
    ): Single<RequestResult<Transaction>>

}