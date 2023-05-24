package com.example.catapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Credentials")
data class Credentials(
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String
)

