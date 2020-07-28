package com.example.converter

import com.example.model.Breed
import com.example.model.CatImage
import com.example.model_realm.RealmBreed
import com.example.model_realm.RealmCatImage
import io.realm.RealmList

class RealmConverter {
fun <T> listToRealmList(list: List<T>?): RealmList<T>? {
        if (list == null) {
            return null
        }

        val resultList = RealmList<T>()
        resultList.addAll(resultList)

        return resultList
    }

    /*private fun <D,R> listToRealmList(domainList: List<D>?, toConvert: (D) -> R): RealmList<R>? {
        if (domainList == null) {
            return null
        }

        val resultList = RealmList<R>()
        domainList.forEach {
            resultList.add(toConvert.invoke(it))
        }
        return resultList
    }*/
}