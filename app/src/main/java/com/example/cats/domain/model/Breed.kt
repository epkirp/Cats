package com.example.cats.domain.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class Breed(

    @PrimaryKey
    open var id: String = "dvIdh",
    open var name: String = "Mongrel",
    open var description: String = "Mongrel cat",
    open var temperament: String = "good cat",
    open var life_span: String = ":)"
) : RealmObject(), Serializable