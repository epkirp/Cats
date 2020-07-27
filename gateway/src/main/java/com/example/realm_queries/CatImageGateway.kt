package com.example.realm_queries

import com.example.model_realm.RealmBreed
import com.example.model_realm.RealmCatImage
import io.realm.Realm
import io.realm.RealmList


class CatImageGateway : CatImageInterface {
    override fun addCatImage(realm: Realm, catImage: RealmCatImage) {
        realm.beginTransaction()
        realm.insertOrUpdate(catImage)
        realm.commitTransaction()
    }

    override fun addAllCatsImages(realm: Realm, imageList: List<RealmCatImage>) {
        imageList.forEach {
            if (it.breed.isNullOrEmpty()) {
                it.breed = RealmList(RealmBreed())
            }
        }

        val realmImages = RealmList<RealmCatImage>()
        realmImages.addAll(imageList)

        realm.beginTransaction()
        realm.insertOrUpdate(imageList)
        realm.commitTransaction()
    }

    override fun editCatImage(realm: Realm, catImage: RealmCatImage) {
        realm.beginTransaction()
        realm.copyToRealm(catImage)
        realm.commitTransaction()
    }

    override fun getCatImage(realm: Realm, id: String): RealmCatImage? {
        return realm.where(RealmCatImage::class.java)
            .equalTo("id", id)
            .findFirst()
    }

    override fun getAllCatImages(realm: Realm): List<RealmCatImage>? {
        return realm.copyFromRealm(
            realm.where(RealmCatImage::class.java)
                .findAll()
        )
    }
}