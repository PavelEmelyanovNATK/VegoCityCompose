
package com.emelyanov.vegocity.modules.main.modules.cart.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.CartDismissValue.Default
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.CartDismissValue.Dismissed
import androidx.compose.material.SwipeableDefaults.StandardResistanceFactor
import androidx.compose.material.SwipeableDefaults.StiffResistanceFactor
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.shared.presentation.components.VerticalVegoCounter
import kotlinx.coroutines.CancellationException
import kotlin.math.roundToInt


enum class CartDismissValue {
    Default,
    Dismissed
}


@ExperimentalMaterialApi
class CartDismissState(
    initialValue: CartDismissValue,
    confirmStateChange: (CartDismissValue) -> Boolean = { true }
) : SwipeableState<CartDismissValue>(initialValue, confirmStateChange = confirmStateChange) {

    fun isDismissed(): Boolean {
        return currentValue == Dismissed
    }

    suspend fun reset() = animateTo(targetValue = Default)

    suspend fun dismiss(direction: DismissDirection) {
        animateTo(targetValue = Dismissed)
    }

    companion object {
        fun Saver(
            confirmStateChange: (CartDismissValue) -> Boolean
        ) = Saver<CartDismissState, CartDismissValue>(
            save = { it.currentValue },
            restore = { CartDismissState(it, confirmStateChange) }
        )
    }
}

@Composable
@ExperimentalMaterialApi
fun rememberCartDismissState(
    initialValue: CartDismissValue = Default,
    confirmStateChange: (CartDismissValue) -> Boolean = { true }
): CartDismissState {
    return rememberSaveable(saver = CartDismissState.Saver(confirmStateChange)) {
        CartDismissState(initialValue, confirmStateChange)
    }
}

private const val IMAGE_WIDTH = 116f
private const val OVERLAP_OFFSET = 16f
@Composable
@ExperimentalMaterialApi
fun CartItem(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    price: Int,
    count: Int,
    onDismiss: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) = BoxWithConstraints(
    modifier
        .fillMaxWidth()
        .height(80.dp)
) {
    val width = with(LocalDensity.current) { -IMAGE_WIDTH.dp.toPx() + OVERLAP_OFFSET.dp.toPx() - 1.dp.toPx() }
    val state = rememberCartDismissState(initialValue = Default) { true }

    if(state.currentValue == Dismissed) {
        onDismiss()
    }

    val anchors = mutableMapOf(
        0f to Default,
        width to Dismissed
    )

    Box(
        Modifier
            .swipeable(
                state = state,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                enabled = state.currentValue == Default,
                resistance = null,
                thresholds = { from, to ->
                    if(from == Default && to == Dismissed) {
                        FractionalThreshold(0.9f)
                    } else {
                        FractionalThreshold(0.1f)
                    }
                }
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.errorContainer)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .width(IMAGE_WIDTH.dp)
                .align(Alignment.CenterStart)
                .background(Color.LightGray),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Product image",
            contentScale = ContentScale.Crop
        )

        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = IMAGE_WIDTH.dp / 2 - 24.dp),
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "Delete icon",
            tint = MaterialTheme.colorScheme.onErrorContainer
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxSize()
                .padding(start = IMAGE_WIDTH.dp - OVERLAP_OFFSET.dp)
                .offset { IntOffset(state.offset.value.roundToInt(), 0) }
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(18.dp),
                text = title,
                style = MaterialTheme.typography.labelLarge
                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp),
                text = "$price",
                style = MaterialTheme.typography.labelMedium
                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            VerticalVegoCounter(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 18.dp),
                value = count,
                onIncrement = { onIncrement() },
                onDecrement = { onDecrement() }
            )
        }
    }
}

