package com.example.citybikes.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citybikes.model.NetworkHref
import com.example.citybikes.repository.NetworkHrefRepo
import kotlinx.coroutines.launch
import java.util.Locale

class NetworkHrefVM: ViewModel() {
    private val assetsRepository = NetworkHrefRepo()
    var asset: MutableState<NetworkHref?> = mutableStateOf(null)
    private var isAssetLoaded = false
    fun fetchAssets(assetId: String) {
        if (!isAssetLoaded) {
            viewModelScope.launch {
                try {
                    val response = assetsRepository.getNetworkHref(assetId).network
                    response?.let { assetResponse ->
                        val countryName = countryCodeToCountryName(assetResponse.location.country)
                        asset.value = NetworkHref(
                            assetResponse.company,
                            assetResponse.href,
                            assetResponse.id,
                            assetResponse.location.city,
                            countryName,
                            assetResponse.name,
                            assetResponse.stations
                        )
                    }
                    isAssetLoaded = true
                } catch (e: java.lang.Exception) {
                    Log.e("GIS FetchAssets", e.message.toString())
                }
            }
        }
    }
    fun countryCodeToCountryName(countryCode: String): String {
        val locale = Locale("es", countryCode)
        return locale.displayCountry
    }
}