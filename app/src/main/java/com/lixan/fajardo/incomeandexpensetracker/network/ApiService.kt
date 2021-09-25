package com.lixan.fajardo.incomeandexpensetracker.network

import com.lixan.fajardo.incomeandexpensetracker.network.response.dto.UserDataTokenDTO
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @FieldMap fields : Map<String, @JvmSuppressWildcards Any>
    ) : Single<UserDataTokenDTO>
}