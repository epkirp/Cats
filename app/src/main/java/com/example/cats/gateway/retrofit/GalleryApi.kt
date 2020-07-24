package com.example.cats.gateway.retrofit

import com.example.cats.domain.model.CatImage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApi {
    // @Headers("x-api-key: DEMO-API-KEY")
    @GET("v1/images/search")
    fun getImages(
        //@Query("page") page: Int,
        @Query("limit") limit: Int = RetrofitApi.LIMIT_PAGE
    ): Observable<List<CatImage>?>
}