package com.example.citybikes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.citybikes.model.Network
import com.example.citybikes.viewModel.NetworkVM
import com.example.citybikes.ui.theme.CityBikesTheme
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import java.util.Locale


fun countryCodeToCountryName(countryCode: String): String {
    val locale = Locale("es", countryCode)
    return locale.displayCountry
}

@Composable
fun NetworkListScreen(viewModel: NetworkVM) {

    val assets = viewModel.assets

    LaunchedEffect(Unit) {
        viewModel.fetchAssets()
    }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
    ) {

        items(assets) {currentAsset ->
            AssetRow(asset = currentAsset)
            Divider()
        }
    }
}

@Composable
fun AssetRow(asset: Network) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {

            if ( LocalInspectionMode.current) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                )
            } else {
                AsyncImage(
                    model = "https://flagcdn.com/48x36/${asset.location.country.lowercase()}.png",
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
        Column {
            Text(
                text = countryCodeToCountryName(asset.location.country),
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = asset.location.city,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = asset.name,
            fontSize = 16.sp,
            color = Color.White
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNetworkListScreen() {

    CityBikesTheme {
        val viewModel: NetworkVM = viewModel()
        NetworkListScreen(viewModel)
    }
}