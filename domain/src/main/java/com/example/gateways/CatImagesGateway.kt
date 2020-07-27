package com.example.gateways

import com.example.model.CatImage
import io.reactivex.Observable

interface CatImagesGateway {
    fun getImages(): Observable<List<CatImage>?>
}