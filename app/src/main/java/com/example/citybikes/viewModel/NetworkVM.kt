package com.example.citybikes.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

import com.example.citybikes.model.Network
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.citybikes.repository.NetworkRepo
import java.util.Locale

class NetworkVM: ViewModel() {
    private val assetsRepository = NetworkRepo()

    var assets = mutableStateListOf<Network>()
    private var isAssetsLoaded = false
    fun fetchAssets() {
        if (!isAssetsLoaded) {
            viewModelScope.launch {
                try {
                    val response = assetsRepository.getNetwork().networks
                    val assetsResponse = response.map { assetResponse ->
                        val countryName = countryCodeToCountryName(assetResponse.location.country)
                        val cityName = assetResponse.location.city
                        val latitude = assetResponse.location.latitude
                        val longitude = assetResponse.location.longitude
                        Network(
                            assetResponse.company,
                            assetResponse.href,
                            assetResponse.id,
                            cityName,
                            countryName,
                            assetResponse.location.country,
                            latitude,
                            longitude,
                            assetResponse.name,
                            assetResponse.source
                        )
                    }
                    assets.addAll(assetsResponse)
                    isAssetsLoaded = true
                } catch (e: java.lang.Exception) {
                    Log.e("GIS FetchAssets", e.message.toString())
                }
                assets.sortBy { asset ->
                    asset.country
                }
            }
        }
    }
    fun countryCodeToCountryName(countryCode: String): String {
        val locale = Locale("es", countryCode)
        return locale.displayCountry
    }
}