package com.emelyanov.vegocity.shared.presentation.components

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import kotlin.math.roundToInt
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.ModalCartDialog
import kotlinx.coroutines.launch

val BottomBarVisibility = mutableStateOf(true)
val BottomBarOffset = mutableStateOf(0f)

@ExperimentalMaterialApi
@Composable
fun FlexScaffold(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable ColumnScope.() -> Unit,
    bottomBar: @Composable () -> Unit,
    bottomBarHeight: Dp = 80.dp,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

//    BackHandler(sheetState.currentValue != ModalBottomSheetValue.Hidden) {
//        coroutineScope.launch {
//            sheetState.hide()
//        }
//        Log.d("FlexScaffold", "custom back handled")
//    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        ModalBottomSheetLayout(
            modifier = Modifier.fillMaxSize(),
            sheetContent = sheetContent,
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetBackgroundColor = MaterialTheme.colorScheme.surface
        ) {
            content()

            val density = LocalDensity.current
            val floatBottomBarHeight = remember { with(density) { bottomBarHeight.toPx() } }
            val bottomBarOffset = animateIntAsState(
                targetValue = if(BottomBarVisibility.value) 0 else floatBottomBarHeight.roundToInt(),
                animationSpec = spring()
            )
            val additionalBottomBarOffset = if(BottomBarOffset.value < 0f) 0 else BottomBarOffset.value.roundToInt()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomBarHeight)
                    .offset { IntOffset(0, bottomBarOffset.value) }
                    .offset { IntOffset(0, additionalBottomBarOffset) }
                    .align(Alignment.BottomStart)
            ) {
                bottomBar()
            }
        }
    }
}