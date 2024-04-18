package com.example.citybikes.view

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.citybikes.service.LocationService
import com.example.citybikes.viewModel.NetworkVM
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLocationScreen(navController: NavHostController, networkVM: NetworkVM) {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    val locationService = remember { LocationService() }
    var userLocation: LatLng? = null
    var nearestLocation: LatLng? = null

    LaunchedEffect(Unit) {
        location = locationService.getUserLocation(context)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Back") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPaddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .background(MaterialTheme.colorScheme.onBackground),
        ) {
            Column {
                Text(
                    text = "Latitude: ${location?.latitude ?: "Loading"} - My Longitude: ${location?.longitude ?: "Loading"}",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                location?.let { location ->
                    userLocation = LatLng(location.latitude, location.longitude)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(userLocation!!, 10f)
                    }
                    val nearestAsset = networkVM.findNearestAssetToLocation(location.latitude, location.longitude)
                    if (nearestAsset != null) {
                        nearestLocation = LatLng(nearestAsset.latitude, nearestAsset.longitude)
                    }
                    val verticalPaddingModifier = Modifier.padding(bottom = 90.dp)
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(verticalPaddingModifier),
                        cameraPositionState = cameraPositionState
                    ){
                        Marker(
                            state = MarkerState(position = userLocation!!),
                            title = "My Location",
                            snippet = "You are here!"
                        )
                        if (nearestAsset != null) {
                            Marker(
                                state = MarkerState(position = nearestLocation!!),
                                title = nearestAsset.name,
                                snippet = nearestAsset.country + " - " + nearestAsset.city,
                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                            )
                        }
                    }
                }

            }

        }
    }
}

