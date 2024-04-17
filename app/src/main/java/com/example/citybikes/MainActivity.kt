package com.example.citybikes

import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.citybikes.service.LocationService
import com.example.citybikes.view.NetworkListScreen
import com.example.citybikes.viewModel.NetworkVM
import com.example.citybikes.ui.theme.CityBikesTheme
import com.example.citybikes.view.MainScreen
import com.example.citybikes.viewModel.NetworkHrefVM
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val networkViewModel: NetworkVM by viewModels()
    private val networkHrefViewModel: NetworkHrefVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CityBikesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(assetsHrefViewModel = networkHrefViewModel , assetsViewModel = networkViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CityBikesTheme {
        Greeting("Android")
    }
}