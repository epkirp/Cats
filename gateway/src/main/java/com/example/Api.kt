package com.example

import com.example.model.CatImage
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("v1/images/search")
    fun getImages(
        //@Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Observable<List<CatImage>?>
}