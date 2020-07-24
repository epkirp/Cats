package com.example.cats.app.ui.presenters

import com.example.cats.domain.model.CatImage
import io.realm.Realm
import io.realm.RealmModel

interface CatImageInterface : RealmModel {
    fun addCatImage(realm: Realm, catImage: CatImage): Boolean
    fun addAllCatsImage(realm: Realm, imageList: List<CatImage>): Boolean
    fun editCatImage(realm: Realm, catImage: CatImage): Boolean
    fun getCatImage(realm: Realm, id: String): CatImage?
    fun getAllCatImage(realm: Realm) : List<CatImage>?
}