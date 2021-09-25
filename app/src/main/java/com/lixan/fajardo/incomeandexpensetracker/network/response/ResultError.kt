package com.lixan.fajardo.incomeandexpensetracker.network.response

data class ResultError(
    val errorMessage: String,
    val cause: Throwable? = null,
    val errorCode: String = ""
)