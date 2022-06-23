package com.emelyanov.vegocity.navigation.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.CatalogViewModel
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.CatalogScreen
import com.emelyanov.vegocity.modules.main.modules.favorites.presentation.FavoritesScreen
import com.emelyanov.vegocity.modules.main.modules.favorites.domain.FavoritesViewModel
import com.emelyanov.vegocity.modules.main.modules.info.presentation.InfoScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@ExperimentalAnimationApi
@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainNavHost(
    mainNavController: NavHostController
) {
    val mainNavHolder: MainNavHolder = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = true) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mainNavHolder.mainNavProvider.observeNavigationFlow(this@repeatOnLifecycle) { destination ->
                destination ?: return@observeNavigationFlow

                if(destination == MainDestinations.PopBack)
                    mainNavController.popBackStack()
                else
                    mainNavController.navigate(destination.route) {
                        launchSingleTop = true
                        popUpTo(mainNavController.graph.startDestinationId)
                    }
            }
        }
    }

    NavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = mainNavController,
        startDestination = MainDestinations.Catalog.route
    ) {
        composable(
            route = MainDestinations.Catalog.route
        ) {
            val viewModel: CatalogViewModel = hiltViewModel()
            val viewState = viewModel.viewState.collectAsState()
            val searchField = viewModel.searchField.collectAsState()

            CatalogScreen(
                viewState = viewState.value,
                searchFieldValue = searchField.value,
                onSearchChange = viewModel::searchFiledChanged,
                onCategoryClick = viewModel::categoryClicked,
                onRefresh = viewModel::reloadToDefault,
                onProductClick = viewModel::onProductClick
            )
        }
        composable(
            route = MainDestinations.Favorites.route
        ) {
            val viewModel: FavoritesViewModel = hiltViewModel()
            val viewState = viewModel.viewState.collectAsState()
            val searchField = viewModel.searchField.collectAsState()

            LaunchedEffect(key1 = true) {
                viewModel.reloadToDefault()
            }

            FavoritesScreen(
                viewState = viewState.value,
                searchField = searchField.value,
                onSearchChanged = viewModel::searchFiledChanged,
                onRefresh = viewModel::reloadToDefault,
            )
        }
        composable(
            route = MainDestinations.AboutUs.route
        ) {
            InfoScreen()
        }
    }
}