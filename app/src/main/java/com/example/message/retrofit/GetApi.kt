package com.example.message.retrofit

import com.example.message.data_model.ChatItem
import retrofit2.http.GET

interface GetApi {
    @GET("v3/744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    suspend fun getChatItems(): List<ChatItem>
}