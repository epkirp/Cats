package com.example.cats.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ImageDao {
    @Query("SELECT * FROM CatsDatabase")
    fun getAll(): LiveData<List<Image>?>

    @Query("SELECT * FROM CatsDatabase WHERE id IN (:id)")
    fun loadAllById(id: Int): List<Image>?

    @Insert
    fun insertAll(image: Image)

    @Delete
    fun delete(image: Image)
}
