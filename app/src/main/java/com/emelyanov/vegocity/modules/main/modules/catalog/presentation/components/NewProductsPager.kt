package com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.ignoreHorizontalParentPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import java.math.RoundingMode
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.nextUp
import kotlin.math.roundToInt

internal val ITEMS_SPACING = 18.dp

@ExperimentalSnapperApi
@ExperimentalPagerApi
@Composable
fun NewProductsPager(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(0),
    items: List<Any>,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyRow(
            modifier = Modifier
                .ignoreHorizontalParentPadding()
                .fillMaxWidth()
                .wrapContentHeight(),
            state = state,
            flingBehavior = rememberSnapperFlingBehavior(
                lazyListState = state,
                snapOffsetForItem = SnapOffsets.Start,
            ),
            contentPadding = PaddingValues(horizontal = ITEMS_SPACING, vertical = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(items = items) {
                NewProductItem()
            }
        }
    }
}


