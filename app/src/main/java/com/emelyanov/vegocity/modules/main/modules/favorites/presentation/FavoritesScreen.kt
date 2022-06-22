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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components.SearchFilterToolBar
import com.emelyanov.vegocity.modules.main.modules.favorites.presentation.components.FavoriteCard
import com.emelyanov.vegocity.modules.main.modules.favorites.domain.FavoritesViewModel
import com.emelyanov.vegocity.modules.main.presentation.components.NAV_BAR_HEIGHT
import com.emelyanov.vegocity.shared.domain.utils.TOOL_BAR_HEIGHT
import com.emelyanov.vegocity.shared.presentation.components.*
import com.example.compose.smallPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalSnapperApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun FavoritesScreen(
    viewState: FavoritesViewModel.ViewState,
    searchField: String,
    onSearchChanged: (String) -> Unit,
    onCategoryClick: (String) -> Unit,
    onProductClick: (String) -> Unit,
    onRefresh: () -> Unit,
    onDeleteClick: (String) -> Unit
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
                searchFieldValue = searchField,
                onValueChange = onSearchChanged
            )
        },
        backLayerContent = {
            CategoriesMenuBlock(viewState = viewState, onCategoryClick = onCategoryClick)
        },
        frontLayerContent = {
            if(viewState is FavoritesViewModel.ViewState.Error) {
                ErrorStateView(
                    message = viewState.message,
                    onRefresh = onRefresh
                )
            }
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
                        ShortCategoriesBlock(
                            viewState = viewState, onCategoryClick = onCategoryClick) {
                            
                        }
                    }

                    productsBlock(
                        viewState = viewState,
                        onProductClick = onProductClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }
    )
}

@ExperimentalAnimationApi
@Composable
private fun ShortCategoriesBlock(
    modifier: Modifier = Modifier,
    viewState: FavoritesViewModel.ViewState,
    onCategoryClick: (String) -> Unit,
    onButtonAllClick: () -> Unit
) = Box(modifier) {
    when(viewState) {
        is FavoritesViewModel.ViewState.Loading -> {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(3) {
                    item(it) {
                        VegoChip(
                            modifier = Modifier
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp),
                                    highlight = PlaceholderHighlight.shimmer(Color.White)
                                ),
                            isChecked = false,
                            onClick = {},
                            text = "placeh"
                        )
                    }
                }
            }
        }
        is FavoritesViewModel.ViewState.Presentation -> {
            val first5categories  = remember(viewState.categories) {
                viewState.categories.take(5)
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentPadding = PaddingValues(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                first5categories.forEach {
                    item(it.id) {
                        VegoChip(
                            isChecked = it.isSelected,
                            onClick = {
                                onCategoryClick(it.id)
                            },
                            text = it.name
                        )
                    }
                }

                item("button_all") {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable(onClick = onButtonAllClick)
                            .padding(
                                vertical = 6.dp,
                                horizontal = 14.dp
                            ),
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
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
private fun CategoriesMenuBlock(
    viewState: FavoritesViewModel.ViewState,
    onCategoryClick: (String) -> Unit
) {
    when(viewState) {
        is FavoritesViewModel.ViewState.Loading -> {
            CategoriesMenu(
                isLoading = true
            )
        }
        is FavoritesViewModel.ViewState.Presentation -> {
            CategoriesMenu(
                categories = viewState.categories,
                onCategoryClick = onCategoryClick,
                isLoading = false
            )
        }
    }
}

private fun LazyGridScope.productsBlock(
    viewState: FavoritesViewModel.ViewState,
    onProductClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    when(viewState) {
        is FavoritesViewModel.ViewState.Loading -> {
            repeat(2) { group ->
                item(
                    key = group,
                    span = { GridItemSpan(this.maxLineSpan) }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp),
                                highlight = PlaceholderHighlight.shimmer(Color.White),
                            ),
                        text = "group.key",
                        style = MaterialTheme.typography.titleLarge
                            .copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                }

                items(
                    items = listOf(1,2,3),
                ) {
                    FavoriteCard(
                        modifier = Modifier
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(16.dp),
                                highlight = PlaceholderHighlight.shimmer(Color.White),
                            ),
                        title = "placeholder",
                        price = 1111,
                        onProductClick = {

                        },
                        onDeleteClick = {
                            
                        }
                    )
                }
            }
        }
        is FavoritesViewModel.ViewState.Presentation -> {
            viewState.products.forEach { group ->
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
                    FavoriteCard(
                        title = it.title,
                        price = it.price,
                        onProductClick = {
                            onProductClick(it.id)
                        },
                        onDeleteClick = {
                            onDeleteClick(it.id)
                        }
                    )
                }
            }
        }
    }
}