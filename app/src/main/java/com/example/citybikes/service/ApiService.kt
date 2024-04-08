package com.example.citybikes.service

import com.example.citybikes.model.NetworkHrefResponse
import com.example.citybikes.model.NetworksResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v2/networks")
    suspend fun getNetworks():NetworksResponse

    @GET("v2/networks/{assetId}")
    suspend fun getNetworksHref(@Path("assetId") assetId: String): NetworkHrefResponse
}