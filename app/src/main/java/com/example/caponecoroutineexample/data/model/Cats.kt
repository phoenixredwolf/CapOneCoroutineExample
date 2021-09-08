package com.example.caponecoroutineexample.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cats(
    val id: String,
    val created_at: String,
    val tags: List<String>,
    val url: String
)
