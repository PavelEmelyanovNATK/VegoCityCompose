package com.emelyanov.vegocity.modules.main.modules.info.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.modules.main.modules.info.presentation.components.BackdropMenu
import com.emelyanov.vegocity.modules.main.modules.info.presentation.components.DropdownToolBar
import com.emelyanov.vegocity.shared.presentation.components.BottomBarOffset
import com.emelyanov.vegocity.shared.presentation.components.BottomBarVisibility
import com.emelyanov.vegocity.shared.utils.TOOL_BAR_HEIGHT
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun InfoScreen(

) {
    val backdropState = rememberBackdropScaffoldState(
        initialValue = BackdropValue.Concealed
    )

    val coroutineScope = rememberCoroutineScope()

    BackHandler(backdropState.currentValue != BackdropValue.Concealed) {
        coroutineScope.launch {
            backdropState.conceal()
        }
    }

    val density = LocalDensity.current
    val pxToolBarHeight = remember { with(density) { TOOL_BAR_HEIGHT.dp.toPx() } }
    BottomBarOffset.value = backdropState.offset.value - pxToolBarHeight

    val sections = linkedSetOf(
        "О нас",
        "Доставка",
        "Совместные покупки",
        "Контакты"
    )
    var currentSection by remember { mutableStateOf(sections.elementAt(0)) }

    BackdropScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = backdropState,
        backLayerBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        frontLayerBackgroundColor = MaterialTheme.colorScheme.background,
        peekHeight = TOOL_BAR_HEIGHT.dp,
        frontLayerElevation = 0.dp,
        appBar = {
            DropdownToolBar(
                title = currentSection,
                isExpanded = { backdropState.isRevealed },
                onButtonClick = {
                    coroutineScope.launch {
                        if(backdropState.isConcealed)
                            backdropState.reveal()
                        else
                            backdropState.conceal()
                    }
                },
                expandProgress = {
                    when {
                        backdropState.overflow.value < 0f && backdropState.isConcealed -> 0f
                        backdropState.overflow.value > 0f && backdropState.isRevealed -> 0f
                        backdropState.direction != 0f -> backdropState.progress.fraction
                        else -> 0f
                    }
                }
            )
        },
        backLayerContent = {
            BackdropMenu(
                items = sections,
                selectedItem = currentSection,
                onItemClick = {
                    currentSection = it
                    coroutineScope.launch {
                        backdropState.conceal()
                    }
                },
                itemIcon = {
                    when(it) {
                        sections.elementAt(0) -> painterResource(R.drawable.ic_about)
                        sections.elementAt(1) -> painterResource(R.drawable.ic_delivery)
                        sections.elementAt(2) -> painterResource(R.drawable.ic_group)
                        else -> painterResource(R.drawable.ic_phone)
                    }
                },
                itemIconDescription = {
                    when(it) {
                        sections.elementAt(0) -> "About us icon"
                        sections.elementAt(1) -> "Delivery icon"
                        sections.elementAt(2) -> "Group icon"
                        else -> "Phone icon"
                    }
                }
            )
        },
        frontLayerContent = {
            when(currentSection) {
                sections.elementAt(0) -> AboutUsSection()
                sections.elementAt(1) -> DeliverySection()
                sections.elementAt(2) -> JointPurchasesSection()
                sections.elementAt(3) -> ContactsSection()
            }
        },
        frontLayerScrimColor = Color.Transparent
    )
}