package com.lixan.fajardo.incomeandexpensetracker.network.response.base

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("error") val message: String = ""
)