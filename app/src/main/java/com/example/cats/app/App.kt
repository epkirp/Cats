package com.example.cats.app

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)
    }

    companion object {
        lateinit var appContext: Context
    }
}