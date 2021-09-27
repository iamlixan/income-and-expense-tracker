package com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.implementation

import com.lixan.fajardo.incomeandexpensetracker.data.models.Transaction
import com.lixan.fajardo.incomeandexpensetracker.network.ApiService
import com.lixan.fajardo.incomeandexpensetracker.network.remoterepository.source.TransactionRemoteRepository
import com.lixan.fajardo.incomeandexpensetracker.network.response.ErrorHandler
import com.lixan.fajardo.incomeandexpensetracker.network.response.RequestResult
import com.lixan.fajardo.incomeandexpensetracker.network.response.ResultError
import com.lixan.fajardo.incomeandexpensetracker.network.response.dto.TransactionDTO
import io.reactivex.Single
import javax.inject.Inject

class TransactionRemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): TransactionRemoteRepository {

    override fun addTransaction(
        token: String,
        type: String,
        subType: String,
        amount: Double,
        notes: String,
        date: String
    ): Single<RequestResult<Transaction>> {
        val transactionMap = hashMapOf<String, Any>()
        transactionMap["type"] = type
        transactionMap["sub_type"] = subType
        transactionMap["amount"] = amount
        transactionMap["notes"] = notes
        transactionMap["date"] = date

        return apiService.addTransaction(
            token,
            transactionMap
        ).map { responseDTO ->
            if (responseDTO.id.isNotBlank()) {
                RequestResult.success(
                    TransactionDTO.mapTransaction(responseDTO)
                )
            } else {
                RequestResult.error(
                    ResultError(responseDTO.message.orEmpty())
                )
            }
        }
            .onErrorReturn {
                RequestResult.error(
                    ErrorHandler.handleError(it)
                )
            }
    }

}