package com.lixan.fajardo.incomeandexpensetracker.network.response.dto

import com.google.gson.annotations.SerializedName
import com.lixan.fajardo.incomeandexpensetracker.data.models.Transaction

data class TransactionDTO (
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("sub_type") val subType: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("notes") val notes: String,
    @SerializedName("date") val date: String,
    @SerializedName("created") val created: String,
    @SerializedName("error") val message: String? = ""
) {
    companion object {
        fun mapTransaction(from: TransactionDTO): Transaction {
            return Transaction(
                id = from.id,
                type = from.type,
                subType = from.subType,
                amount = from.amount,
                notes = from.notes,
                date = from.date,
                created = from.created
            )
        }
    }
}