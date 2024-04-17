package com.example.citybikes.view

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.citybikes.service.LocationService

@Composable
fun myLocationScreen() {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    val locationService = remember { LocationService() }
    LaunchedEffect(Unit) {
        location = locationService.getUserLocation(context)
    }
    Column {
        Text(text = "Location: ${location?.latitude ?: "Loading"}, ${location?.longitude ?: "Loading"}")
    }
}