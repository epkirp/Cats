package com.example.cats.app.retrofit

import com.example.Api
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    private const val BASE_URL = "https://api.thecatapi.com/"

    private  lateinit var client: OkHttpClient
    private lateinit var retrofit: Retrofit

    init {
        buildClient()
        buildRetrofit()
        getApi()
    }

    private fun buildClient() {
        client = OkHttpClient
            .Builder()
            .build()
    }

    private fun buildRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getApi(): Api {
        return retrofit.create(Api::class.java)
    }
}