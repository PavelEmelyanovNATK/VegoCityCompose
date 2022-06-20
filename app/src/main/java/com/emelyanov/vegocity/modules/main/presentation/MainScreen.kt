package com.emelyanov.vegocity.modules.main.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.swipeable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.emelyanov.vegocity.modules.main.modules.cart.domain.CartViewModel
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.ModalCartDialog
import com.emelyanov.vegocity.modules.main.presentation.components.VegoNavBar
import com.emelyanov.vegocity.navigation.main.MainNavHost
import com.emelyanov.vegocity.shared.presentation.components.FlexScaffold
import com.example.compose.VegoCityTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun MainScreen(

) {
    val mainNavController = rememberNavController()
    Box {
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()

        FlexScaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                VegoNavBar(
                    mainNavController = mainNavController,
                    onCartClick = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    }
                )
            },
            sheetState = sheetState,
            sheetContent = {
                BackHandler(sheetState.currentValue != ModalBottomSheetValue.Hidden) {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }

                if(sheetState.currentValue == ModalBottomSheetValue.Hidden && sheetState.offset.value != 0f || sheetState.currentValue != ModalBottomSheetValue.Hidden) {
                    val viewModel: CartViewModel = hiltViewModel()
                    val viewState = viewModel.viewState.collectAsState()
                    ModalCartDialog(viewState = viewState.value)
                } else Box(Modifier.size(10.dp))
            }
        ) {
            MainNavHost(mainNavController = mainNavController)
        }
    }
}


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
@Preview
private fun Preview() {
    VegoCityTheme {
        MainScreen()
    }
}