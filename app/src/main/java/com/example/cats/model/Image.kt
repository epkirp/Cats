package com.example.cats.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CatsDatabase")
class Image(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "url")
    val url: String,

    @TypeConverters(DataConverter::class)
    @ColumnInfo(name = "breed")
    val breed: List<Breed>
)