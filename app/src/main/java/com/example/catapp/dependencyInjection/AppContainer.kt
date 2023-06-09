package com.example.catapp.dependencyInjection

import android.content.Context
import com.example.catapp.data.CredentialsDatabase
import com.example.catapp.data.CredentialsRepositoryImplementation
import com.example.catapp.data.DefaultDogRepository
import com.example.catapp.data.DogRepository
import com.example.catapp.domain.CredentialsRepository
import com.example.catapp.network.DogApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {

val credentialsRepository : CredentialsRepository
    val dogRepository: DogRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val BASE_URL =
        "https://dog.ceo"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }
    override val dogRepository: DogRepository by lazy {
        DefaultDogRepository(retrofitService)
    }

    override val credentialsRepository: CredentialsRepository by lazy {
        CredentialsRepositoryImplementation(CredentialsDatabase.getDatabase(context).credentialsDao())
    }

}