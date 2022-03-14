package com.example.flowersapp.utils

import android.app.Application

class ApplicationClass : Application() {

    companion object {
        lateinit var instance: ApplicationClass
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
