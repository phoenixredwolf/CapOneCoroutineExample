package com.example.caponecoroutineexample.data.remote

import com.example.caponecoroutineexample.data.model.Cats
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("cat/says/{text}")
    suspend fun getCatWithText(
        @Path("text") text: String,
        @Query("json") json: Boolean = true,
        @Query("size") size: String,
        @Query("color") color: String,
        @Query("filter") filter: String
    ): Response<Cats>

    @GET("cat/{tag}/says/{text}")
    suspend fun getCatWithAll(
        @Path("tag") tag: String,
        @Path("text") text: String,
        @Query("json") json: Boolean = true,
        @Query("size") size: String,
        @Query("color") color: String,
        @Query("filter") filter: String
    ): Response<Cats>

    @GET("cat/{tag}")
    suspend fun getCatWithTag(
        @Path("tag") tag: String,
        @Query("json") json: Boolean = true,
        @Query("filter") filter: String
    ): Response<Cats>

    @GET("cat")
    suspend fun getCat(
        @Query("json") json: Boolean = true,
        @Query("filter") filter: String
    ): Response<Cats>

}