package com.example.citybikes.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citybikes.model.NetworkHref
import com.example.citybikes.model.NetworkHrefResponse
import com.example.citybikes.model.NetworkX
import com.example.citybikes.model.Station
import com.example.citybikes.repository.NetworkHrefRepo
import kotlinx.coroutines.launch
import java.util.Locale

class NetworkHrefVM: ViewModel() {
    private val assetsRepository = NetworkHrefRepo()
    //var assetNetworkHref = mutableListOf<NetworkHref>()
    var assetNetworkHref: NetworkHref? = null

    fun fetchAssets(assetId: String) {
        viewModelScope.launch {
            try {
                val response = assetsRepository.getNetworkHref(assetId).network
                assetNetworkHref = mapAssetResponseToNetworkHref(response)
            } catch (e: java.lang.Exception) {
                Log.e("GIS FetchAssets", e.message.toString())
            }
        }
    }
    private fun mapAssetResponseToNetworkHref(assetResponse: NetworkX): NetworkHref {
        val countryName = countryCodeToCountryName(assetResponse.location.country)
        return NetworkHref(
            company = assetResponse.company,
            href = assetResponse.href,
            id = assetResponse.id,
            city = assetResponse.location.city,
            country = countryName,
            countryId = assetResponse.location.country,
            name = assetResponse.name,
            stations = assetResponse.stations
        )
    }
    private fun countryCodeToCountryName(countryCode: String): String {
        val locale = Locale("es", countryCode)
        return locale.displayCountry
    }
}