package com.lixan.fajardo.incomeandexpensetracker.network.response.base

import com.google.gson.annotations.SerializedName

data class BaseErrorResponse(
    @SerializedName("error") val message: String
)