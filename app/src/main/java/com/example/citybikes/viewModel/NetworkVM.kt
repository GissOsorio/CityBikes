package com.example.citybikes.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

import com.example.citybikes.model.Network
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.citybikes.repository.NetworkRepo
import java.util.Locale
import kotlin.math.*

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
    private fun countryCodeToCountryName(countryCode: String): String {
        val locale = Locale("es", countryCode)
        return locale.displayCountry
    }

    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val radius = 6371 // Radio de la Tierra en kilómetros
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return radius * c
    }

    fun findNearestAssetToLocation(latitude: Double, longitude: Double): Network? {
        var nearestAsset: Network? = null
        var minDistance = Double.MAX_VALUE

        for (asset in assets) {
            val distance = calculateDistance(latitude, longitude, asset.latitude, asset.longitude)
            if (distance < minDistance) {
                minDistance = distance
                nearestAsset = asset
            }
        }

        return nearestAsset
    }
}