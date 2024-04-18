package com.example.citybikes.view

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.citybikes.service.LocationService
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MyLocationScreen() {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    val locationService = remember { LocationService() }
    LaunchedEffect(Unit) {
        location = locationService.getUserLocation(context)
    }
    Column {
        Text(
            text = "Location: ${location?.latitude ?: "Loading"}, ${location?.longitude ?: "Loading"}"
        )
        if (location != null) {
            val userLocation = LatLng(location!!.latitude, location!!.longitude)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(userLocation, 10f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = userLocation),
                    title = "My Location",
                    snippet = "You are here"
                )
            }
        }
    }
}