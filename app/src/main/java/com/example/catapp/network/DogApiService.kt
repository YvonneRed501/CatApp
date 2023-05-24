package com.example.catapp.network

import com.example.catapp.model.Dog
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


interface DogApiService {
    @GET("/api/breeds/image/random/50")
    suspend fun getDogs(): Dog
}

