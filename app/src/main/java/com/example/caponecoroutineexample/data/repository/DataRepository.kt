package com.example.caponecoroutineexample.data.repository

import com.example.caponecoroutineexample.BuildConfig.BASE_URL
import com.example.caponecoroutineexample.data.remote.ApiManager
import com.example.caponecoroutineexample.utility.Resource

class DataRepository {

    suspend fun getCatWithText(
        text: String,
        json: Boolean,
        size: String,
        color: String,
        filter: String
    ) : Resource<List<String>> {
        return try {
            val catResponse = ApiManager.getCatWithText(text, json, size, color, filter)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                val returnedURL = catResponse.body()!!.url
                val url = BASE_URL + returnedURL
                Resource.Success(listOf(url))
            } else {
                Resource.Error("No cat found")
            }
        } catch (ex: Exception) {
            Resource.Error("Connection error: $ex")
        }
    }

    suspend fun getCatWithAll(
        tag: String,
        text: String,
        json: Boolean,
        color: String,
        size: String,
        filter: String
    ) : Resource<List<String>> {
        return try {
            val catResponse = ApiManager.getCatWithAll(tag, text, json, color, size, filter)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                val returnedURL = catResponse.body()!!.url
                val url = BASE_URL + returnedURL
                Resource.Success(listOf(url))
            } else {
                Resource.Error("No cat found")
            }
        } catch (ex: Exception) {
            Resource.Error("Connection error: $ex")
        }
    }

    suspend fun getCatWithTag(
        tag: String,
        json: Boolean,
        filter: String
    ) : Resource<List<String>> {
        return try {
            val catResponse = ApiManager.getCatWithTag(tag, json, filter)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                val returnedURL = catResponse.body()!!.url
                val url = BASE_URL + returnedURL
                Resource.Success(listOf(url))
            } else {
                Resource.Error("No cat found")
            }
        } catch (ex: Exception) {
            Resource.Error("Connection error: $ex")
        }
    }

    suspend fun getCat(
        json: Boolean,
        filter: String
    ) : Resource<List<String>> {
        return try {
            val catResponse = ApiManager.getCat(json, filter)
            if (catResponse.isSuccessful && catResponse.body() != null) {
                val returnedURL = catResponse.body()!!.url
                val url = BASE_URL + returnedURL
                Resource.Success(listOf(url))
            } else {
                Resource.Error("No cat found")
            }
        } catch (ex: Exception) {
            Resource.Error("Connection error: $ex")
        }
    }

}