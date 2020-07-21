package com.example.cats

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.cats.model.AppDatabase


class App : Application() {

    override fun onCreate() {
        /*super.onCreate()
        instance = this*/
        appContext = applicationContext

        /*//for Room
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .build()*/
    }

    companion object {
        lateinit var appContext: Context
        /*lateinit var instance: App
        lateinit var database: AppDatabase*/
    }

}