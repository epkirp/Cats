package com.example.converter

import com.example.model.Breed
import com.example.model.CatImage
import com.example.model_realm.RealmBreed
import com.example.model_realm.RealmCatImage
import io.realm.RealmList

class RealmConverter : RealmConverterInterface {
    override fun imageCatFromRealm(catImage: RealmCatImage) : CatImage {
        return CatImage(catImage.id, catImage.url, breedFromRealmList(catImage.breed))
    }
    override fun imageCatToRealm(catImage: CatImage) : RealmCatImage {
        return RealmCatImage(catImage.id, catImage.url, breedToRealmList(catImage.breed))
    }

    override fun breedFromRealm(breed: RealmBreed) : Breed {
        return Breed(breed.id, breed.name, breed.description, breed.temperament, breed.lifeSpan)
    }
    override fun breedToRealm(breed: Breed) : RealmBreed {
        return RealmBreed(breed.id, breed.name, breed.description, breed.temperament, breed.lifeSpan)
    }

    override fun imageCatToRealmList(catImages: List<CatImage>) : List<RealmCatImage> {
        var resultList = ArrayList<RealmCatImage>()

        catImages.forEach {
            resultList.add(imageCatToRealm(it))
        }
        return resultList
    }
    override fun imageCatFromRealmList(catImages: List<RealmCatImage>?) : List<CatImage>? {
        if (catImages == null) {
            return null
        }

        var resultList = ArrayList<CatImage>()

        catImages.forEach {
            resultList.add(imageCatFromRealm(it))
        }
        return resultList
    }

    override fun breedFromRealmList(breeds: RealmList<RealmBreed>?) : List<Breed>? {
        if (breeds == null) {
            return null
        }

        var resultList = ArrayList<Breed>()

        breeds.forEach {
            resultList.add(breedFromRealm(it))
        }
        return resultList
    }

    override fun breedToRealmList(breeds: List<Breed>?) : RealmList<RealmBreed>? {
        if (breeds == null) {
            return null
        }

        var resultList = RealmList<RealmBreed>()

        breeds.forEach {
            resultList.add(breedToRealm(it))
        }
        return resultList
    }

    private fun <D,R> listToRealmList(domainList: List<D>?, toConvert: (D) -> R): RealmList<R>? {
        if (domainList == null) {
            return null
        }

        val resultList = RealmList<R>()
        domainList.forEach {
            resultList.add(toConvert.invoke(it))
        }
        return resultList
    }
}