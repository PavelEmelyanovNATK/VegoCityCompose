package com.emelyanov.vegocity.shared.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun DetailScreenBackdrop(
    modifier: Modifier = Modifier,
    backgroundHeight: Dp = 0.dp,
    backgroundCornerRadius: Dp = 16.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    backgroundContent: @Composable () -> Unit,
    foregroundColor: Color = MaterialTheme.colorScheme.background,
    foregroundContent: @Composable () -> Unit,
    backgroundScrimColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    swipeableState: SwipeableState<Boolean> = rememberSwipeableState(initialValue = true)
) {
    val pxHeight = with(LocalDensity.current) { backgroundHeight.toPx() }
    val anchors = mapOf(
        0f to false,
        pxHeight to true
    )

    val swipeProgress = when {
        swipeableState.direction != 0f -> swipeableState.progress.fraction
        else -> 0f
    }

    val scrimAlpha = if(swipeableState.currentValue == true) swipeProgress else 1f - swipeProgress

    Box(
        modifier = modifier
            .background(backgroundColor)
    ) {
        Box {
            backgroundContent()
            Box(
                modifier = modifier
                    .matchParentSize()
                    .drawBehind {
                        drawRect(
                            backgroundScrimColor.copy(alpha = scrimAlpha)
                        )
                    }
            )
        }

        Surface(
            modifier = Modifier
                .matchParentSize()
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Vertical,
                    resistance = null
                )
                .offset { IntOffset(x = 0, y = swipeableState.offset.value.roundToInt()) }
                .nestedScroll(swipeableState.PreUpPostDownNestedScrollConnection),
            color = foregroundColor,
            shape = RoundedCornerShape(topEnd = backgroundCornerRadius, topStart = backgroundCornerRadius),
            content = foregroundContent
        )
    }
}

internal var minBound = Float.NEGATIVE_INFINITY

@ExperimentalMaterialApi
val <T> SwipeableState<T>.PreUpPostDownNestedScrollConnection: NestedScrollConnection
    get() = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.toFloat()
            return if (delta < 0 && source == NestedScrollSource.Drag) {
                performDrag(delta).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                performDrag(available.toFloat()).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            return if (toFling < 0 && offset.value > minBound) {
                performFling(velocity = toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return available
        }
    }

internal fun Float.toOffset(): Offset = Offset(0f, this)

internal fun Offset.toFloat(): Float = this.y
