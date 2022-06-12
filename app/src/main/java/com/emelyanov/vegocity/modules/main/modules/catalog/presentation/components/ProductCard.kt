package com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R

internal val PRODUCT_CARD_HEIGHT = 140.dp
internal val CORNER_RADIUS = 16.dp

@Composable
fun ProductCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(PRODUCT_CARD_HEIGHT)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(PRODUCT_CARD_HEIGHT/3 * 2 + CORNER_RADIUS)
                .clip(RoundedCornerShape(topStart = CORNER_RADIUS, topEnd = CORNER_RADIUS)),
            painter = painterResource(R.drawable.test_image),
            contentDescription = "Product image",
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(PRODUCT_CARD_HEIGHT/3 + CORNER_RADIUS)
                .clip(RoundedCornerShape(CORNER_RADIUS))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                text = "Title",
                style = MaterialTheme.typography.labelLarge
                    .copy(color = MaterialTheme.colorScheme.onSecondaryContainer)
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                text = "123142",
                style = MaterialTheme.typography.labelMedium
                    .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}