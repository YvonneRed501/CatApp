package com.example.catapp.data

import com.example.catapp.model.Dog
import com.example.catapp.network.DogApiService

interface DogRepository {
    suspend fun getDogs(): Dog
}

class DefaultDogRepository( private val dogApiService: DogApiService) : DogRepository {
    override suspend fun getDogs(): Dog {
       return dogApiService.getDogs()
    }
}