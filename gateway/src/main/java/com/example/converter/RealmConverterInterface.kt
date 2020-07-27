package com.example.converter

import com.example.model.Breed
import com.example.model.CatImage
import com.example.model_realm.RealmBreed
import com.example.model_realm.RealmCatImage
import io.realm.RealmList

interface RealmConverterInterface {

    fun imageCatFromRealm(catImage: RealmCatImage) : CatImage
    fun imageCatToRealm(catImage: CatImage) : RealmCatImage

    fun breedFromRealm(breed: RealmBreed) : Breed
    fun breedToRealm(breed: Breed) : RealmBreed

    fun imageCatToRealmList(catImages: List<CatImage>) : List<RealmCatImage>
    fun imageCatFromRealmList(catImages: List<RealmCatImage>?) : List<CatImage>?

    fun breedFromRealmList(breeds: RealmList<RealmBreed>?) : List<Breed>?
    fun breedToRealmList(breeds: List<Breed>?) : RealmList<RealmBreed>?
}