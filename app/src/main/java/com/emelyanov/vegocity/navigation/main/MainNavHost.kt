package com.emelyanov.vegocity.navigation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.CatalogScreen
import com.emelyanov.vegocity.modules.main.modules.favorites.presentation.FavoritesScreen
import com.emelyanov.vegocity.modules.main.modules.info.presentation.InfoScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavHost(
    mainNavController: NavHostController
) {
    NavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = mainNavController,
        startDestination = MainDestinations.Catalog.route
    ) {
        composable(
            route = MainDestinations.Catalog.route
        ) {
            CatalogScreen()
        }
        composable(
            route = MainDestinations.Favorites.route
        ) {
            FavoritesScreen()
        }
        composable(
            route = MainDestinations.AboutUs.route
        ) {
            InfoScreen()
        }
    }
}