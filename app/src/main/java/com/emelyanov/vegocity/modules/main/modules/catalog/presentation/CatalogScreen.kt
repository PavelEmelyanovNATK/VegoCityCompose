package com.emelyanov.vegocity.modules.main.modules.catalog.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.CatalogViewModel
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components.SearchFilterToolBar
import com.emelyanov.vegocity.shared.presentation.components.CategoriesMenu
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components.NewProductsPager
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components.ProductCard
import com.emelyanov.vegocity.modules.main.presentation.components.NAV_BAR_HEIGHT
import com.emelyanov.vegocity.shared.presentation.components.BottomBarOffset
import com.emelyanov.vegocity.shared.presentation.components.SwipeableIndicator
import com.emelyanov.vegocity.shared.presentation.components.VegoChip
import com.emelyanov.vegocity.shared.utils.TOOL_BAR_HEIGHT
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

typealias ViewState = CatalogViewModel.ViewState
typealias DefaultViewState = CatalogViewModel.ViewState.Default
typealias FilterViewState = CatalogViewModel.ViewState.Filter

typealias NewProductsVS = CatalogViewModel.NewProductsViewState
typealias NewProductsPresentationVS = CatalogViewModel.NewProductsViewState.Presentation
typealias NewProductsLoadingVS = CatalogViewModel.NewProductsViewState.Loading
typealias NewProductsEmptyVS = CatalogViewModel.NewProductsViewState.Empty
typealias NewProductsErrorVS = CatalogViewModel.NewProductsViewState.Error
typealias NewProductsPresentVS = CatalogViewModel.NewProductsViewState.Presentation

typealias ProductsVS = CatalogViewModel.ProductsViewState
typealias ProductsPresentationVS = CatalogViewModel.ProductsViewState.Presentation
typealias ProductsErrorVS = CatalogViewModel.ProductsViewState.Error
typealias ProductsLoadingVS = CatalogViewModel.ProductsViewState.Loading
typealias ProductsEmptyVS = CatalogViewModel.ProductsViewState.Empty
typealias ProductsPresentVS = CatalogViewModel.ProductsViewState.Presentation


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalSnapperApi
@ExperimentalPagerApi
@Composable
fun CatalogScreen(
    viewState: ViewState,
    searchFieldValue: String = "",
    onSearchChange: (String) -> Unit = {},
    onCategoryClick: (String) -> Unit
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
                searchFieldValue = searchFieldValue,
                onValueChange = onSearchChange
            )
        },
        backLayerContent = {
            when(viewState) {
                is DefaultViewState -> CategoriesMenuBlock(
                    viewState = viewState.productsViewState,
                    onCategoryClick = onCategoryClick
                )
                is FilterViewState -> CategoriesMenuBlock(
                    viewState = viewState.productsViewState,
                    onCategoryClick = onCategoryClick
                )
            }
        },
        frontLayerContent = {
            when(viewState) {
                is DefaultViewState -> DefaultScreenState(
                    viewState = viewState,
                    onCategoryClick = onCategoryClick,
                    onButtonAllClick = {
                        coroutineScope.launch {
                            backdropState.reveal()
                        }
                    }
                )
                is FilterViewState -> FilterScreenState(
                    viewState = viewState,
                    onCategoryClick = onCategoryClick,
                    onButtonAllClick = {
                        coroutineScope.launch {
                            backdropState.reveal()
                        }
                    }
                )
            }
        }
    )
}

@ExperimentalPagerApi
@ExperimentalSnapperApi
@Composable
fun NewProductsBlock(
    viewState: NewProductsVS
) {
    when(viewState) {
        is NewProductsEmptyVS -> {

        }
        is NewProductsLoadingVS -> {

        }
        is NewProductsErrorVS -> {

        }
        is NewProductsPresentationVS -> {
            NewProductsPager(items = viewState.newProducts)
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ShortCategoriesBlock(
    modifier: Modifier = Modifier,
    viewState: ProductsVS,
    onCategoryClick: (String) -> Unit,
    onButtonAllClick: () -> Unit
) = Box(modifier) {
        when(viewState) {
            is ProductsEmptyVS -> {
                TODO()
            }
            is ProductsErrorVS -> {
                TODO()
            }
            is ProductsLoadingVS -> {
                TODO()
            }
            is ProductsPresentationVS -> {
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
fun CategoriesMenuBlock(
    viewState: ProductsVS,
    onCategoryClick: (String) -> Unit
) {
    when(viewState) {
        is ProductsEmptyVS -> {
            TODO()
        }
        is ProductsErrorVS -> {
            TODO()
        }
        is ProductsLoadingVS -> {
            TODO()
        }
        is ProductsPresentationVS -> {
            CategoriesMenu(
                categories = viewState.categories,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

fun LazyGridScope.productsBlock(
    viewState: ProductsVS
) {
    when(viewState) {
        is ProductsEmptyVS -> {
            TODO()
        }
        is ProductsErrorVS -> {
            TODO()
        }
        is ProductsLoadingVS -> {
            TODO()
        }
        is ProductsPresentationVS -> {
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
                    ProductCard(
                        title = it.title,
                        price = it.price,
                        isNew = it.isNew
                    )
                }
            }
        }
    }
}

@ExperimentalSnapperApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun DefaultScreenState(
    viewState: DefaultViewState,
    onCategoryClick: (String) -> Unit,
    onButtonAllClick: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()

    val wholePageScrollingEnabled by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex == 0 || lazyGridState.isScrollInProgress
        }
    }

    val gridScrollingEnabled by remember(viewState, lazyListState) {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 3 ||lazyGridState.firstVisibleItemIndex != 0 || lazyGridState.isScrollInProgress
        }
    }

    Column {
        SwipeableIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp)
        )

        BoxWithConstraints {
            val maxHeight = maxHeight

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                userScrollEnabled =  wholePageScrollingEnabled
            ) {
                item(
                    key = "title1",
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 18.dp, start = 18.dp),
                        text = "Специальное предложние",
                        style = MaterialTheme.typography.titleLarge
                            .copy(color = MaterialTheme.colorScheme.onBackground)
                    )
                }

                item(
                    key = "new_products"
                ) {
                    NewProductsBlock(viewState = viewState.newProductsViewState)
                }

                item(
                    key = "categories",
                ) {
                    ShortCategoriesBlock(
                        modifier = Modifier.padding(18.dp),
                        viewState = viewState.productsViewState,
                        onCategoryClick = onCategoryClick,
                        onButtonAllClick = onButtonAllClick
                    )
                }

                item("products") {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .height(maxHeight),
                        columns = GridCells.Adaptive(150.dp),
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp),
                        contentPadding = PaddingValues(bottom = NAV_BAR_HEIGHT.dp + 18.dp, start = 18.dp, end = 18.dp),
                        userScrollEnabled = gridScrollingEnabled
                    ) {
                        productsBlock(viewState = viewState.productsViewState)
                    }
                }
            }
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun FilterScreenState(
    viewState: FilterViewState,
    onCategoryClick: (String) -> Unit,
    onButtonAllClick: () -> Unit
) {
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
                //key = "categories",
                span = { GridItemSpan(this.maxLineSpan) }
            ) {
                ShortCategoriesBlock(
                    viewState = viewState.productsViewState,
                    onCategoryClick = onCategoryClick,
                    onButtonAllClick = onButtonAllClick
                )
            }

            productsBlock(viewState = viewState.productsViewState)
        }
    }
}