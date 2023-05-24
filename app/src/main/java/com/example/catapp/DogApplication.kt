package com.example.catapp

import android.app.Application
import android.content.Context
import com.example.catapp.data.AppContainer
import com.example.catapp.data.DefaultAppContainer



class DogApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}