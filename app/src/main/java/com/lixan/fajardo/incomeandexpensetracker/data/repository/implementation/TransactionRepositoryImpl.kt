package com.lixan.fajardo.incomeandexpensetracker.data.repository.implementation

import com.lixan.fajardo.incomeandexpensetracker.data.models.Transaction
import com.lixan.fajardo.incomeandexpensetracker.data.repository.source.TransactionRepository
import com.lixan.fajardo.incomeandexpensetracker.local.source.LoginLocalRepository
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source.TransactionRemoteRepository
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import io.reactivex.Single
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val local: LoginLocalRepository,
    private val remote: TransactionRemoteRepository
): TransactionRepository {

    override fun addTransaction(
        type: String,
        subType: String,
        amount: Double,
        notes: String,
        date: String
    ): Single<RequestResult<Transaction>> {

        return local.getBearerToken()
            .flatMap { bearerToken ->
                remote.addTransaction(
                    bearerToken,
                    type,
                    subType,
                    amount,
                    notes,
                    date
                )
            }
    }

}