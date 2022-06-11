package com.emelyanov.vegocity.navigation.main

import androidx.activity.compose.BackHandler
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emelyanov.vegocity.modules.main.modules.info.presentation.InfoScreen
import com.emelyanov.vegocity.modules.main.modules.productdetails.presentation.ProductDetailsScreen

@ExperimentalMaterialApi
@Composable
fun MainNavHost(
    mainNavController: NavHostController
) {
    NavHost(
        navController = mainNavController,
        startDestination = MainDestinations.Catalog.route
    ) {
        composable(
            route = MainDestinations.Catalog.route
        ) {
            ProductDetailsScreen()
        }
        composable(
            route = MainDestinations.Favorites.route
        ) {

        }
        composable(
            route = MainDestinations.AboutUs.route
        ) {
            InfoScreen()
        }
    }
}