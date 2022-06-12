package com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R

internal val NEW_PRODUCT_ITEM_WIDTH = 150.dp

@Composable
fun NewProductItem(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .width(150.dp)
            .height(200.dp),
        painter = painterResource(R.drawable.test_image),
        contentDescription = "New product card",
        contentScale = ContentScale.Crop
    )
}