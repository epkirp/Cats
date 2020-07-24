package com.example.cats.domain.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.*
import java.io.Serializable

@RealmClass
open class CatImage(

    @PrimaryKey
    open var id: String = "",
    open var url: String = "",
    @SerializedName("breeds")
    open var breed: RealmList<Breed>? = null
) : RealmObject(), Serializable {

    fun getFirstBreed(): Breed? {
        return breed?.firstOrNull()
    }
}