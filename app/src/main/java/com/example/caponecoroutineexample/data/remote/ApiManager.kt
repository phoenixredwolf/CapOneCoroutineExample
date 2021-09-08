package com.example.caponecoroutineexample.data.remote

import com.example.caponecoroutineexample.data.model.Cats
import com.example.caponecoroutineexample.di.NetworkModule
import retrofit2.Response

object ApiManager {

    private val service: ApiService
    private val retrofit = NetworkModule.provideRetrofit()

    init {
        service = retrofit.create(ApiService::class.java)
    }

    suspend fun getCatWithText(
        text: String = "",
        json: Boolean = true,
        size: String,
        color: String,
        filter: String
    ): Response<Cats> {
        return service.getCatWithText(text, json, size, color, filter)
    }

    suspend fun getCatWithAll(
        tag: String = "",
        text: String,
        json: Boolean = true,
        color: String,
        size: String,
        filter: String
    ): Response<Cats> {
        return service.getCatWithAll(tag, text, json, size, color, filter)
    }

    suspend fun getCatWithTag(
        tag: String = "",
        json: Boolean = true,
        filter: String
    ): Response<Cats> {
        return service.getCatWithTag(tag, json, filter)
    }

    suspend fun getCat(
        json: Boolean = true,
        filter: String
    ): Response<Cats> {
        return service.getCat(json, filter)
    }
}