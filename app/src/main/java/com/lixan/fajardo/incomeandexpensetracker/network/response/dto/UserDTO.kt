package com.lixan.fajardo.incomeandexpensetracker.network.response.dto

import com.google.gson.annotations.SerializedName

data class  UserDTO(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("created") val createdOn: String,
    @SerializedName("email") val email: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("id") val id : String
)