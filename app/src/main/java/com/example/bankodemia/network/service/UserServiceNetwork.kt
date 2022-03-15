package com.example.bankodemia.network.service

import com.example.bankodemia.core.RetrofitBankodemiaInstance
import com.example.bankodemia.core.retrofit.RetrofitExceptionHandler
import com.example.bankodemia.data.model.BankodemiaErrorResponse
import com.example.bankodemia.data.model.User
import com.example.bankodemia.network.api.UserAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserServiceNetwork {
    private val retrofit = RetrofitBankodemiaInstance.getRetrofit().create(UserAPI::class.java)
    private lateinit var exceptionHandler: RetrofitExceptionHandler

    suspend fun getUserProfileInfo(): User.UserProfile? {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getUserProfileInfo()
            val responseBody = response.body() ?: throw exceptionHandler.createApiExeption(response, BankodemiaErrorResponse::class.java)
            responseBody
        }
    }
}