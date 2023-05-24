package com.example.catapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(credentials: Credentials)

    @Update
    suspend fun update(credentials: Credentials)

    @Delete
    suspend fun delete(credentials: Credentials)

    @Query("SELECT EXISTS(SELECT * FROM Credentials WHERE username = :username)")
    suspend fun checkUsernameExists(username: String): Boolean


    @Query("SELECT EXISTS(SELECT * FROM Credentials WHERE username = :username AND password = :password)")
    suspend fun checkCredentials(username: String, password: String): Boolean


//ELECT EXISTS(SELECT * FROM credentials WHERE username = :username) AND (SELECT * FROM credentials WHERE password = :password)
}