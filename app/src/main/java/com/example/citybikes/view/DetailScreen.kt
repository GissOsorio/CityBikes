package com.example.citybikes.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.citybikes.model.Network
import androidx.compose.foundation.lazy.LazyColumn
import com.example.citybikes.model.NetworkHref
import com.example.citybikes.model.Station
import com.example.citybikes.viewModel.NetworkHrefVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: NetworkHrefVM, assetId: String, navController: NavHostController) {
    val asset = viewModel.assetNetworkHref
    LaunchedEffect(Unit) {
        viewModel.fetchAssets(assetId)
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
            asset?.let { asset ->
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    AssetCard(asset)
                    stationTitle()
                    LazyColumn {
                        items(asset.stations) { currentStation ->
                            AssetRowStation(station = currentStation) { station ->
                                val longitude = station.longitude.toString()
                                val latitude = station.latitude.toString()
                                navController.navigate("${BottomNavItem.Home.route}/${longitude}/${latitude}")
                            }
                            Divider()
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun AssetCard(asset: NetworkHref) {
    Box(
        modifier = Modifier
            .size(width = 350.dp, height = 250.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OutlinedCard(
            border = BorderStroke(1.dp, Color.White),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        if ( LocalInspectionMode.current) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(width = 108.dp, height = 81.dp)
                            )
                        } else {
                            AsyncImage(
                                model = "https://flagcdn.com/108x81/${asset.countryId.lowercase()}.png",
                                contentDescription = null,
                                modifier = Modifier
                                    .size(width = 108.dp, height = 81.dp)
                            )
                        }
                    }
                    Text(
                        text = asset.city + " - " + asset.country,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = asset.name,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }
    }



}

@Composable
fun AssetRowStation(station: Station, onClick:(Station) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick(station) }
    ) {

        Text(
            text = station.name,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = station.free_bikes.toString(),
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun stationTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "Stations",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Free bikes",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
    Divider()
}
