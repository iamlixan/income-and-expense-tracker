package com.lixan.fajardo.incomeandexpensetracker.data.models

import com.google.gson.annotations.SerializedName

data class Transaction(
    val id: String,
    val type: String,
    val subType: String,
    val amount: Double,
    val notes: String,
    val date: String,
    val created: String
) {
    companion object {
        fun empty(): Transaction {
            return Transaction(
                "",
                "",
                "",
                0.0,
                "",
                "",
                ""
            )
        }
    }
}