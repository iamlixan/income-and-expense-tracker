package com.lixan.fajardo.incomeandexpensetracker.network.response.dto

import com.google.gson.annotations.SerializedName
import com.lixan.fajardo.incomeandexpensetracker.data.models.User
import com.lixan.fajardo.incomeandexpensetracker.data.models.UserDataToken

data class UserDataTokenDTO(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: UserDTO,
    @SerializedName("error") val message: String? = ""
){
    companion object {
        fun mapUserDataToken(from: UserDataTokenDTO): UserDataToken {
            return UserDataToken(
                token = from.token,
                user = User (
                    firstName = from.user.firstName,
                    createdOn = from.user.createdOn,
                    email = from.user.email,
                    lastName = from.user.lastName,
                    id = from.user.id
                )
            )
        }
    }
}