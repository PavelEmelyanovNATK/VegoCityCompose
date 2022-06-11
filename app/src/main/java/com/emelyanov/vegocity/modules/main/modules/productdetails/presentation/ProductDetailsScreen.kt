package com.emelyanov.vegocity.modules.main.modules.productdetails.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.modules.main.presentation.components.NAV_BAR_HEIGHT
import com.emelyanov.vegocity.shared.presentation.components.DetailScreenBackdrop
import com.emelyanov.vegocity.shared.presentation.components.HorizontalVegoCounter
import com.example.compose.smallPadding

private const val IMAGE_HEIGHT = 216
private const val OVERLAP_OFFSET = 16

@ExperimentalMaterialApi
@Composable
fun ProductDetailsScreen(

) {
    val swipeState = rememberSwipeableState(initialValue = true)
    val swipeProgress = when {
        swipeState.direction != 0f -> swipeState.progress.fraction
        else -> 0f
    }

    val swipeStateInfluence = if(swipeState.currentValue == true) swipeProgress else 1f - swipeProgress

    DetailScreenBackdrop(
        modifier = Modifier.fillMaxSize(),
        swipeableState = swipeState,
        backgroundHeight = (IMAGE_HEIGHT - OVERLAP_OFFSET).dp,
        backgroundContent = {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IMAGE_HEIGHT.dp),
                painter = painterResource(R.drawable.test_image),
                contentDescription = "Product photo",
                contentScale = ContentScale.Crop
            )
        },
        foregroundContent = {
            Column {
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 1f - swipeStateInfluence))
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(6.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = swipeStateInfluence))
                    )
                }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Title",
                            style = MaterialTheme.typography.titleLarge
                                .copy(color = MaterialTheme.colorScheme.onBackground)
                        )

                        val interactionSource = remember { MutableInteractionSource() }
                        Icon(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = rememberRipple(bounded = false, radius = 24.dp),
                                    onClick = {}
                                ),
                            painter = painterResource(R.drawable.ic_favorite),
                            contentDescription = "Favorites button",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            text = "asdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdaddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddasdasdasdasdasdadsasdasdadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd",
                            style = MaterialTheme.typography.bodyLarge
                                .copy(color = MaterialTheme.colorScheme.onBackground)
                        )

                        Text(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(4.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(end = 14.dp),
                            text = "12331",
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
                            HorizontalVegoCounter(value = 12, onIncrement = { /*TODO*/ }) {

                            }

                            Spacer(Modifier.weight(1f))

                            Button(
                                onClick = { },
                                contentPadding = ButtonDefaults.smallPadding
                            ) {
                                Text(
                                    text = "В корзину",
                                    style = MaterialTheme.typography.labelLarge
                                        .copy(MaterialTheme.colorScheme.onPrimary)
                                )
                            }
                        }

                        Spacer(Modifier.height(NAV_BAR_HEIGHT.dp))
                        Spacer(Modifier.height(18.dp))
                    }
                }
        }
    )
}