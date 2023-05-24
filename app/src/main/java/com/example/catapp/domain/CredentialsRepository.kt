package com.example.catapp.domain

import com.example.catapp.data.Credentials

interface CredentialsRepository {
    suspend fun checkCredentials(username: String, password: String): Boolean

    suspend fun checkUsernameExists(username: String): Boolean

    suspend fun insertCredentials(username: String, password: String)

    suspend fun updateCredentials(username: String, password: String)

    suspend fun deleteCredentials(username: String, password: String)
}
