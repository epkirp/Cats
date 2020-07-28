package com.example.model_realm

import com.example.converter.RealmConverter
import com.example.model.CatImage
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RealmCatImage(

    @PrimaryKey
    open var id: String = "",
    open var url: String = "",
    @SerializedName("breeds")
    open var breed: RealmList<RealmBreed>? = null
) : RealmObject() {
    fun toDomain(): CatImage {
        return CatImage(id, url, breed?.map { it.toDomain() })
    }

    companion object {
        fun fromDomain(catImage: CatImage): RealmCatImage {
            return RealmCatImage(
                id = catImage.id,
                url = catImage.url,
                breed = RealmConverter().listToRealmList(catImage.breed?.map {
                    RealmBreed.fromDomain(it)
                })
            )
        }
    }
}