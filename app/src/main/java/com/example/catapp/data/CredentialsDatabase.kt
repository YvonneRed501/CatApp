package com.example.catapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[Credentials::class], version = 1, exportSchema = false)
abstract class CredentialsDatabase : RoomDatabase() {
    abstract fun credentialsDao(): CredentialsDao


    companion object {
        @Volatile
        private var Instance: CredentialsDatabase? = null
        fun getDatabase(context: Context): CredentialsDatabase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(context, CredentialsDatabase::class.java,"credentials.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}