package com.example.citybikes.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

import com.example.citybikes.model.Network
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.citybikes.repository.NetworkRepo
class NetworkVM: ViewModel() {
    private val assetsRepository = NetworkRepo()

    var assets = mutableStateListOf<Network>()
    fun fetchAssets() {
        viewModelScope.launch {
            try {
                val response = assetsRepository.getNetwork().networks
                val assetsResponse = response.map { assetResponse ->
                    Network(
                        assetResponse.company,
                        assetResponse.ebikes,
                        assetResponse.gbfs_href,
                        assetResponse.href,
                        assetResponse.id,
                        assetResponse.license,
                        assetResponse.location,
                        assetResponse.name,
                        assetResponse.source
                    )
                }
                assets.addAll(assetsResponse)
            } catch (e: java.lang.Exception) {
                Log.e("GIS FetchAssets", e.message.toString())
            }
        }
    }
}