package com.example.citybikes.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.citybikes.viewModel.NetworkHrefVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: NetworkHrefVM, assetId: String, navController: NavHostController) {
    val asset = viewModel.asset.value
    LaunchedEffect(Unit) {
        viewModel.fetchAssets(assetId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = assetId) },
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
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            asset?.let { currentAsset ->
                OutlinedCard(
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier
                        .size(width = 350.dp, height = 300.dp),
                ) {
                    Text(
                        text = currentAsset.name,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }

}