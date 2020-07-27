package com.example.model

data class CatImage(
    val id: String,
    val url: String,
    val breed: List<Breed>?
)