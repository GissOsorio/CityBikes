package com.example.citybikes.repository

import com.example.citybikes.model.NetworksResponse
import com.example.citybikes.service.RetrofitObject

class NetworkRepo {
    private val assetsService = RetrofitObject.assetsService
    suspend fun getNetwork():NetworksResponse{
        return assetsService.getNetworks()
    }
}
