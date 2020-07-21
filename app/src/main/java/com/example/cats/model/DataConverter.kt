package com.example.cats.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromBreedList(breeds: List<Breed>?): String? {
        /*if (breeds == null) {
            return null
        }

        var type = object : TypeToken<List<Breed>>() {}.type
        return Gson().toJson(breeds, type)*/

        return Gson().toJson(breeds)
    }

    @TypeConverter
    fun toBreedList(breeds: String?): List<Breed>? {
        if (breeds == null) {
            return emptyList()
        }

        var type = object : TypeToken<List<Breed>>() {}.type

        return Gson().fromJson(breeds, type)
    }
}