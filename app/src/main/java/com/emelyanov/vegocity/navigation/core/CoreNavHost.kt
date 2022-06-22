package com.emelyanov.vegocity.navigation.core

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.CatalogScreen
import com.emelyanov.vegocity.modules.main.modules.favorites.presentation.FavoritesScreen
import com.emelyanov.vegocity.modules.main.modules.info.presentation.InfoScreen
import com.emelyanov.vegocity.modules.main.presentation.MainScreen
import com.emelyanov.vegocity.modules.orderregistration.domain.OrderRegistrationViewModel
import com.emelyanov.vegocity.modules.orderregistration.presentation.OrderRegistrationScreen
import com.emelyanov.vegocity.modules.productdetails.domain.ProductDetailsViewModel
import com.emelyanov.vegocity.modules.productdetails.presentation.ProductDetailsScreen
import com.emelyanov.vegocity.navigation.main.MainDestinations
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalSnapperApi
@ExperimentalAnimationApi
@Composable
fun CoreNavHost(
    coreNavController: NavHostController,
) {
    val coreNavHolder: CoreNavHolder = hiltViewModel()

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = true) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            coreNavHolder.coreNavProvider.observeNavigationFlow(this@repeatOnLifecycle) { destination ->
                destination ?: return@observeNavigationFlow

                if(destination == CoreDestinations.PopBack)
                    coreNavController.popBackStack()
                else
                    coreNavController.navigate(destination.route) { launchSingleTop = true }
            }
        }
    }

    NavHost(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        navController = coreNavController,
        startDestination = CoreDestinations.Main.route
    ) {
        composable(
            route = CoreDestinations.Main.route
        ) {
            MainScreen()
        }
        composable(
            route = CoreDestinations.ProductDetails(null).route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val viewModel: ProductDetailsViewModel = hiltViewModel()
            val viewState = viewModel.viewState.collectAsState()

            val id = requireNotNull(it.arguments?.getString("id"))

            LaunchedEffect(key1 = true) {
                viewModel.loadInfo(id)
            }

            ProductDetailsScreen(
                viewState = viewState.value,
                onRefresh = {
                    viewModel.loadInfo(id)
                }
            )
        }
        composable(
            route = CoreDestinations.OrderRegistration.route
        ) {
            val viewModel: OrderRegistrationViewModel = hiltViewModel()
            val viewState = viewModel.viewState.collectAsState()
            OrderRegistrationScreen(
                viewState = viewState.value,
                onBackClick = viewModel::onBackClick
            )
        }
    }
}