package com.example.cats.retrofit

import com.example.cats.model.Image
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GalleryApi {
   // @Headers("x-api-key: DEMO-API-KEY")
    @GET("v1/images/search")
    fun getImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int = RetrofitApi.LIMIT_PAGE
    ): Call<List<Image>?>
}