package com.example.citybikes.repository

import com.example.citybikes.model.NetworkModel
import com.example.citybikes.utils.RetrofitObject

class NetworkRepo {
    private val apiService = RetrofitObject.apiService
    suspend fun getNetwork():NetworkModel{
        return apiService.getNetworks()
    }
}
