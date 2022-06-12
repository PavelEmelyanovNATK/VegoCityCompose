package com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.shared.presentation.components.VegoTextField
import com.emelyanov.vegocity.shared.utils.TOOL_BAR_HEIGHT
import com.example.compose.VegoCityTheme


@ExperimentalMaterialApi
@Composable
fun SearchFilterToolBar(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TOOL_BAR_HEIGHT.dp)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        VegoTextField(
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            value = "",
            onValueChange = {},
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            textStyle = MaterialTheme.typography.bodyLarge
                .copy(color = MaterialTheme.colorScheme.onSurface),
            placeholder = {
                Text(
                    text = "Поиск товара",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(end = 14.dp),
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Search icon",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
        )

        Icon(
            modifier = Modifier
                .padding(start = 18.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(false, 24.dp),
                    onClick = onFilterClick
                ),
            painter = painterResource(R.drawable.ic_filter),
            contentDescription = "Filter icon",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
private fun Preview() {
    VegoCityTheme {

    }
}