package com.example.model_realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class RealmBreed(

    @PrimaryKey
    open var id: String = "dvIdh",
    open var name: String = "Mongrel",
    open var description: String = "Mongrel cat",
    open var temperament: String = "good cat",
    open var lifeSpan: String = ":)"
) : RealmObject()