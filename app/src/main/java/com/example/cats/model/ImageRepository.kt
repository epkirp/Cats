package com.example.cats.model

import androidx.lifecycle.LiveData

class ImageRepository(private val imageDao: ImageDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allImages: LiveData<List<Image>?> = imageDao.getAll()
 
     fun insertAll(image: Image) {
        imageDao.insertAll(image)
    }
}