package com.example.catapp.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable

data class Dog(
    @SerialName(value = "message")
    val imgSrc: List<String>,
    val status: String
)
