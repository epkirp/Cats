package com.example.model_realm

import com.example.model.Breed
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RealmBreed(

    @PrimaryKey
    open var id: String = "dvIdh",
    open var name: String = "Mongrel",
    open var description: String = "Mongrel cat",
    open var temperament: String = "good cat",
    open var lifeSpan: String = ":)"
) : RealmObject() {
    fun toDomain(): Breed {
        return Breed(id, name, description, temperament, lifeSpan)
    }

    companion object {
        fun fromDomain(breed: Breed): RealmBreed {
            return RealmBreed(
                id = breed.id,
                name = breed.name,
                description = breed.description,
                temperament = breed.temperament,
                lifeSpan = breed.lifeSpan
            )
        }
    }
}