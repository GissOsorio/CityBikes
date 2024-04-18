package com.example.citybikes.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import com.example.citybikes.viewModel.NetworkHrefVM
import com.example.citybikes.viewModel.NetworkVM

@Composable
fun HomeScreen(viewModel: NetworkVM, navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        NetworkListScreen(viewModel, navController)
    }
}

@Composable
fun ProfileScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("Profile Screen")
    }
}



@Composable
fun NavigationGraph(assetsHrefViewModel: NetworkHrefVM,assetsViewModel: NetworkVM, navController: NavHostController) {
    val assetIdKey = "assetId"
    val longitudeKey = "longitudeKey"
    val latitudeKey = "latitudeKey"
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) { HomeScreen(assetsViewModel, navController) }
        composable(BottomNavItem.Favourites.route) { MyLocationScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
        composable("${BottomNavItem.Home.route}/{$assetIdKey}") {backStackEntry ->
            DetailScreen( assetsHrefViewModel,
                assetId = backStackEntry.arguments?.getString(assetIdKey) ?: "missing asset",
                navController
            )
        }
        composable("${BottomNavItem.Home.route}/{$longitudeKey}/{$latitudeKey}") { backStackEntry ->
            val assetLongitude = backStackEntry.arguments?.getString(longitudeKey) ?: "missing asset"
            val assetLatitude = backStackEntry.arguments?.getString(latitudeKey) ?: "missing asset"
            StationLocationScreen(assetLongitude, assetLatitude)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(assetsHrefViewModel: NetworkHrefVM, assetsViewModel: NetworkVM) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar =  { BottomTabBar(navController = navController) }
    ) {
        NavigationGraph(assetsHrefViewModel,assetsViewModel, navController)
    }
}

@Composable
fun BottomTabBar(navController: NavHostController) {
    val tabBarItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favourites,
        BottomNavItem.Profile
    )

    BottomAppBar {
        val navBackStack by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStack?.destination?.route

        tabBarItems.forEach { barItem ->
            val isSelected = currentRoute?.startsWith(barItem.route) ?: false
            NavigationBarItem(
                selected = isSelected,
                label = {  Text(text = barItem.title) },
                onClick = {
                    navController.navigate(barItem.route) {
                        navController.graph.startDestinationRoute.let { route ->
                            if (route != null) {
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                        }

                        // evitar que se recomponga la misma ruta
                        launchSingleTop = true

                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) barItem.selectedIcon else barItem.unselectedIcon,
                        contentDescription = barItem.title
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMain() {
    MainScreen(assetsHrefViewModel = NetworkHrefVM(), assetsViewModel = NetworkVM())
}