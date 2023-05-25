package com.example.catapp

import android.app.Application
import com.example.catapp.dependencyInjection.AppContainer
import com.example.catapp.dependencyInjection.DefaultAppContainer



class DogApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}