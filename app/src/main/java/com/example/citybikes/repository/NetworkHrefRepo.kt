package com.example.citybikes.repository

import com.example.citybikes.model.NetworkHrefResponse
import com.example.citybikes.model.NetworksResponse
import com.example.citybikes.service.RetrofitObject

class NetworkHrefRepo {
    private val assetsService = RetrofitObject.assetsService
    suspend fun getNetworkHref(assetId: String): NetworkHrefResponse {
        return assetsService.getNetworksHref(assetId)
    }
}