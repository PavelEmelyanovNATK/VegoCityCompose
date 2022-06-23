package com.emelyanov.vegocity.modules.productdetails.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.modules.productdetails.domain.ProductDetailsViewModel
import com.emelyanov.vegocity.shared.presentation.components.DetailScreenBackdrop
import com.emelyanov.vegocity.shared.presentation.components.ErrorStateView
import com.emelyanov.vegocity.shared.presentation.components.HorizontalVegoCounter
import com.emelyanov.vegocity.shared.presentation.components.SwipeableIndicator
import com.example.compose.smallPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import io.iamjosephmj.flinger.bahaviours.StockFlingBehaviours
import kotlin.math.abs

private const val IMAGE_HEIGHT = 216
private const val OVERLAP_OFFSET = 16

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun ProductDetailsScreen(
    viewState: ProductDetailsViewModel.ViewState,
    onRefresh: () -> Unit
) {
    val swipeState = rememberSwipeableState(initialValue = true)
    val pagerState = rememberPagerState(0)

    val swipeProgress = when {
        swipeState.direction != 0f -> swipeState.progress.fraction
        else -> 0f
    }

    val swipeStateInfluence = if(swipeState.currentValue == true) swipeProgress else 1f - swipeProgress

    val imageHeight = if(viewState is ProductDetailsViewModel.ViewState.Presentation &&
            viewState.photosUrls.isNotEmpty()) (IMAGE_HEIGHT - OVERLAP_OFFSET).dp
    else 1.dp


    Box {
        DetailScreenBackdrop(
            modifier = Modifier.fillMaxSize(),
            swipeableState = swipeState,
            backgroundHeight = imageHeight,
            backgroundContent = {
                if(viewState is ProductDetailsViewModel.ViewState.Presentation && viewState.photosUrls.isNotEmpty()) {
                    ImageBlock(viewState, pagerState)
                }
            },
            foregroundContent = {
                DescriptionBlock(
                    viewState = viewState,
                    pagerState = pagerState,
                    onRefresh = onRefresh,
                    swipeStateInfluence = { swipeStateInfluence }
                )
            }
        )
        if(viewState is ProductDetailsViewModel.ViewState.Presentation) {
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(end = 14.dp),
                    text = "${viewState.price} руб",
                    style = MaterialTheme.typography.labelLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.End
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalVegoCounter(
                        value = viewState.totalCount,
                        onIncrement = { viewState.onCountChange(it) },
                        onDecrement = { viewState.onCountChange(it) }
                    )

                    Spacer(Modifier.weight(1f))

                    Button(
                        onClick = {
                            viewState.onAddToCart()
                        },
                        contentPadding = ButtonDefaults.smallPadding
                    ) {
                        Text(
                            text = "В корзину",
                            style = MaterialTheme.typography.labelLarge
                                .copy(MaterialTheme.colorScheme.onPrimary)
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun ImageBlock(
    viewState: ProductDetailsViewModel.ViewState.Presentation,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .height(IMAGE_HEIGHT.dp),
        count = viewState.photosUrls.size,
        state = pagerState
    ) { page ->
        val image = viewState.photosUrls[page]
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(IMAGE_HEIGHT.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            loading = {
                Box {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }
            },
            contentDescription = "Product photo",
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun DescriptionBlock(
    viewState: ProductDetailsViewModel.ViewState,
    pagerState: PagerState,
    onRefresh: () -> Unit,
    swipeStateInfluence: () -> Float
) {
    when(viewState) {
        is ProductDetailsViewModel.ViewState.Error -> ErrorStateView(
            message = viewState.message,
            onRefresh = onRefresh
        )
        is ProductDetailsViewModel.ViewState.Loading -> {
            Column {
                Text(
                    modifier = Modifier
                        .padding(18.dp)
                        .placeholder(
                        visible = true,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp),
                        highlight = PlaceholderHighlight.shimmer(Color.White)
                    ),
                    text = "viewState.title",
                    style = MaterialTheme.typography.titleLarge
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(Color.White)
                        ),
                    text = "ddddddddd",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(Color.White)
                        ),
                    text = "dddddddddddddddddddd",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(Color.White)
                        ),
                    text = "ddddddddd",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(Color.White)
                        ),
                    text = "dddddddssdd",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(8.dp),
                            highlight = PlaceholderHighlight.shimmer(Color.White)
                        ),
                    text = "dddddddddddddddd",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(color = MaterialTheme.colorScheme.onBackground)
                )
            }
        }
        is ProductDetailsViewModel.ViewState.Presentation -> {
            Column {
                val dotsAlpha = when(viewState.photosUrls.size) {
                    0 -> 0f
                    1 -> 0f
                    else -> 1f - swipeStateInfluence()
                }

                val swipeIndicatorAlpha = when(viewState.photosUrls.size) {
                    0 -> 0f
                    1 -> 1f
                    else -> swipeStateInfluence()
                }

                BoxWithConstraints(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    CircularTabBar(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .alpha(dotsAlpha),
                        pagerState = pagerState
                    )

                    SwipeableIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .alpha(swipeIndicatorAlpha)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = viewState.title,
                        style = MaterialTheme.typography.titleLarge
                            .copy(color = MaterialTheme.colorScheme.onBackground)
                    )

                    val interactionSource = remember { MutableInteractionSource() }
                    val icon = if(viewState.isFavorite) R.drawable.ic_favorite_checked else R.drawable.ic_favorite
                    Icon(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(bounded = false, radius = 24.dp),
                                onClick = viewState.onAddToFavorites
                            ),
                        painter = painterResource(icon),
                        contentDescription = "Favorites button",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(
                            state = rememberScrollState(),
                            flingBehavior = StockFlingBehaviours.getAndroidNativeScroll()
                        )
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        text = viewState.description,
                        style = MaterialTheme.typography.bodyLarge
                            .copy(color = MaterialTheme.colorScheme.onBackground)
                    )

                    Spacer(Modifier.height(80.dp))
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun CircularTabBar(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    val unselectedColor = MaterialTheme.colorScheme.surfaceVariant
    val selectedColor = MaterialTheme.colorScheme.onSurfaceVariant
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(pagerState.pageCount) {
            val colorAlpha = remember {
                derivedStateOf {
                    when {
                        pagerState.currentPage == it -> 1f - abs(pagerState.currentPageOffset)
                        pagerState.targetPage == it -> abs(pagerState.currentPageOffset)
                        else -> 0f
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .drawBehind {
                        drawCircle(unselectedColor)
                        drawCircle(selectedColor.copy(alpha = colorAlpha.value))
                    }
            )
        }
    }
}