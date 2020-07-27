package com.example.realm_queries

import com.example.model_realm.RealmCatImage
import io.realm.Realm
import io.realm.RealmModel

interface CatImageInterface : RealmModel {
    fun addCatImage(realm: Realm, catImage: RealmCatImage)
    fun addAllCatsImages(realm: Realm, imageList: List<RealmCatImage>)
    fun editCatImage(realm: Realm, catImage: RealmCatImage)
    fun getCatImage(realm: Realm, id: String): RealmCatImage?
    fun getAllCatImages(realm: Realm) : List<RealmCatImage>?
}