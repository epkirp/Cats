package com.example.cats.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Image::class], version = 1)

@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cats_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}