package com.example.cats.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class Breed(
    val id: Int,
    val name: String,
    val temperament: String,
    val life_span: String
)