package com.example.citybikes.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private val BASE_URL = "https://api.citybik.es/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val assetsService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}