package com.example.cats.app.ui.presenters

import com.example.cats.domain.model.Breed
import com.example.cats.domain.model.CatImage
import io.realm.Realm
import io.realm.RealmList


class CatImageGateway : CatImageInterface {
    override fun addCatImage(realm: Realm, catImage: CatImage): Boolean {
        return try {
            realm.beginTransaction()
            realm.insertOrUpdate(catImage)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun addAllCatsImage(realm: Realm, imageList: List<CatImage>): Boolean {
        return try {

            imageList.forEach {
                if (it.breed.isNullOrEmpty()) {
                    it.breed = RealmList(Breed())
                }
            }

            val realmImages: RealmList<CatImage> = RealmList<CatImage>()

            realmImages.addAll(imageList)

            realm.beginTransaction()
            realm.insertOrUpdate(imageList)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun editCatImage(realm: Realm, catImage: CatImage): Boolean {
        return try {
            realm.beginTransaction()
            realm.copyToRealm(catImage)
            realm.commitTransaction()
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getCatImage(realm: Realm, id: String): CatImage? {
        return realm.where(CatImage::class.java)
            .equalTo("id", id)
            .findFirst()
    }


    override fun getAllCatImage(realm: Realm): List<CatImage>? {
        return realm.copyFromRealm(
            realm.where(CatImage::class.java)
                .findAll()
        )
    }
}