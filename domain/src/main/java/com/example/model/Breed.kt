package com.example.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Breed(
     val id: String,
     val name: String,
     val description: String,
     val temperament: String,
     @SerializedName("life_span")
     val lifeSpan: String
) :Serializable