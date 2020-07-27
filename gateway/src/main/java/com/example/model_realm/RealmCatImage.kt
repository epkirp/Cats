package com.example.model_realm

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
) : RealmObject()