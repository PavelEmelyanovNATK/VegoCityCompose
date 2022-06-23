package com.emelyanov.vegocity.modules.main.modules.favorites.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components.CORNER_RADIUS
import com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components.PRODUCT_CARD_HEIGHT

@Composable
fun FavoriteCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    price: Int,
    onProductClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CORNER_RADIUS))
            .height(PRODUCT_CARD_HEIGHT)
            .clickable(onClick = onProductClick)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(PRODUCT_CARD_HEIGHT / 2 + CORNER_RADIUS)
                .clip(RoundedCornerShape(topStart = CORNER_RADIUS, topEnd = CORNER_RADIUS))
                .background(Color.LightGray),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Product image",
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(PRODUCT_CARD_HEIGHT / 2 )
                .clip(RoundedCornerShape(CORNER_RADIUS))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp, top = 8.dp),
                    text = title,
                    style = MaterialTheme.typography.labelLarge
                        .copy(color = MaterialTheme.colorScheme.onSecondaryContainer),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )

                Text(
                    modifier = Modifier
                        .padding(start = 14.dp, bottom = 8.dp),
                    text = price.toString(),
                    style = MaterialTheme.typography.labelMedium
                        .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            Icon(
                modifier = Modifier
                    .padding(14.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(false, 24.dp),
                        onClick = onDeleteClick
                    ),
                painter = painterResource(R.drawable.ic_delete),
                contentDescription = "Delete icon",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

    }
}