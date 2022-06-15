package com.emelyanov.vegocity.modules.main.modules.favorites.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.shared.presentation.components.CategoriesMenu
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components.SearchFilterToolBar
import com.emelyanov.vegocity.modules.main.modules.favorites.presentation.components.FavoriteCard
import com.emelyanov.vegocity.modules.main.presentation.components.NAV_BAR_HEIGHT
import com.emelyanov.vegocity.shared.presentation.components.BottomBarOffset
import com.emelyanov.vegocity.shared.presentation.components.SwipeableIndicator
import com.emelyanov.vegocity.shared.presentation.components.VegoChip
import com.emelyanov.vegocity.shared.utils.TOOL_BAR_HEIGHT
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalSnapperApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun FavoritesScreen(

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

    BackdropScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = backdropState,
        backLayerBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        frontLayerBackgroundColor = MaterialTheme.colorScheme.background,
        peekHeight = TOOL_BAR_HEIGHT.dp,
        frontLayerElevation = 0.dp,
        frontLayerScrimColor = Color.Transparent,
        frontLayerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        appBar = {
            SearchFilterToolBar(
                onFilterClick = {
                    coroutineScope.launch {
                        if(backdropState.isConcealed)
                            backdropState.reveal()
                        else
                            backdropState.conceal()
                    }
                },
                searchFieldValue = "",
                onValueChange = {}
            )
        },
        backLayerContent = {
            CategoriesMenu(categories = listOf(), onCategoryClick = { })
        },
        frontLayerContent = {
            Column {
                SwipeableIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(12.dp)
                )

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    columns = GridCells.Adaptive(150.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    contentPadding = PaddingValues(bottom = NAV_BAR_HEIGHT.dp + 18.dp, start = 18.dp, end = 18.dp),
                ) {
                    item(
                        key = "categories",
                        span = { GridItemSpan(this.maxLineSpan) }
                    ) {
                        val first5categories  = remember(groupedProducts) {
                            groupedProducts.keys.take(5).toList()
                        }

                        LazyRow(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentPadding = PaddingValues(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            first5categories.forEach {
                                item(it) {
                                    VegoChip(isChecked = false, onClick = { /*TODO*/ }, text = it)
                                }
                            }

                            item("button_all") {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .clickable {
                                            coroutineScope.launch {
                                                backdropState.reveal()
                                            }
                                        }
                                        .padding(vertical = 6.dp, horizontal = 14.dp),
                                ) {
                                    Text(
                                        text = "Все",
                                        style = MaterialTheme.typography.labelMedium
                                            .copy(color = MaterialTheme.colorScheme.onPrimary)
                                    )
                                }
                            }
                        }
                    }

                    groupedProducts.forEach { group ->
                        item(
                            key = group.key,
                            span = { GridItemSpan(this.maxLineSpan) }
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 10.dp),
                                text = group.key,
                                style = MaterialTheme.typography.titleLarge
                                    .copy(color = MaterialTheme.colorScheme.onBackground)
                            )
                        }

                        items(
                            items = group.value,
                        ) {
                            FavoriteCard()
                        }
                    }
                }
            }
        }
    )
}

val groupedProducts = mapOf(
    "Вегальмени" to (1..3).toList(),
    "Вареники" to (1..7).toList(),
    "Манты" to (1..6).toList(),
    "Чай" to (1..12).toList(),
    "Кофе" to (1..5).toList(),
    "Масло" to (1..8).toList(),
    "Блины" to (1..9).toList(),
)