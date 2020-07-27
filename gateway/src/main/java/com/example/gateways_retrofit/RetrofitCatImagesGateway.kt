package com.example.gateways_retrofit

import com.example.Api
import com.example.gateways.CatImagesGateway
import com.example.model.CatImage
import io.reactivex.Observable

class RetrofitCatImagesGateway(private val api: Api) : CatImagesGateway {
    override fun getImages(): Observable<List<CatImage>?> {
        return api.getImages()
    }
}