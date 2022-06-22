package com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emelyanov.vegocity.R

internal val PRODUCT_CARD_HEIGHT = 150.dp
internal val CORNER_RADIUS = 16.dp

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    price: Int,
    isNew: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(CORNER_RADIUS))
            .height(PRODUCT_CARD_HEIGHT)
            .clickable(onClick = onClick)
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(PRODUCT_CARD_HEIGHT / 2)
                .clip(RoundedCornerShape(CORNER_RADIUS))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp, top = 8.dp, end = 14.dp),
                text = title,
                style = MaterialTheme.typography.labelLarge
                    .copy(color = MaterialTheme.colorScheme.onSecondaryContainer),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )

            Text(
                modifier = Modifier
                    .padding(start = 14.dp, bottom = 8.dp, end = 14.dp),
                text = "$price руб",
                style = MaterialTheme.typography.labelMedium
                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }

        if(isNew)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 2.dp, y = (-2).dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp, bottomStart = 2.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(vertical = 2.dp, horizontal = 6.dp)
        ) {
            Text(
                text = "Новинка",
                style = MaterialTheme.typography.labelSmall
                    .copy(color = MaterialTheme.colorScheme.onTertiary)
            )
        }
    }
}