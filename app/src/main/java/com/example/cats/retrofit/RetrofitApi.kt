package com.example.cats.retrofit

import com.example.cats.App
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    val galleryApi: GalleryApi by lazy(LazyThreadSafetyMode.NONE) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .addInterceptor(ChuckInterceptor(App.appContext))

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GalleryApi::class.java)
    }

    private const val BASE_URL = "https://api.thecatapi.com/"
    const val MEDIA_URL = "https://api.thecatapi.com/v1/images/"
    const val LIMIT_PAGE = 30
}