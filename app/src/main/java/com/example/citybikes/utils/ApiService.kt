package com.example.citybikes.utils

import com.example.citybikes.model.NetworkModel
import retrofit2.http.GET

interface ApiService {

    @GET("v2/networks")
    suspend fun getNetworks():NetworkModel
}