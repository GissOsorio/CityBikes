package com.example.citybikes.view

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Divider
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
import com.example.citybikes.model.Network
import com.example.citybikes.service.LocationService
import com.example.citybikes.viewModel.NetworkVM
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyLocationScreen(navController: NavHostController) {
    val context = LocalContext.current
    var location by remember { mutableStateOf<Location?>(null) }
    val locationService = remember { LocationService() }
    var userLocation: LatLng
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
                    text = "My Latitude: ${location?.latitude ?: "Loading"} - My Longitude: ${location?.longitude ?: "Loading"}",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                if (location != null) {
                    userLocation = LatLng(location!!.latitude, location!!.longitude)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(userLocation, 10f)
                    }
                    val verticalPaddingModifier = Modifier.padding(bottom = 90.dp)
                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(verticalPaddingModifier),
                        cameraPositionState = cameraPositionState
                    ){
                        Marker(
                            state = MarkerState(position = userLocation),
                            title = "My Location",
                            snippet = "You are here!"
                        )
                    }
                }

            }

        }
    }
}

