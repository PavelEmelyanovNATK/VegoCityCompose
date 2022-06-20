package com.emelyanov.vegocity.shared.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R


@ExperimentalMaterialApi
@Composable
fun VerticalVegoCounter(
    modifier: Modifier = Modifier,
    value: Int,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    val outlineWidth = with(LocalDensity.current) { 2.dp.toPx() }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.tertiary )
                .clickable { onIncrement(value + 1) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(8.dp),
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "Plus icon",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }

        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = value.toString(),
            style = MaterialTheme.typography.labelSmall
                .copy(color = MaterialTheme.colorScheme.onSurface)
        )

        Box(
            modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable { onDecrement(value - 1) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_minus),
                contentDescription = "Minus icon",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun HorizontalVegoCounter(
    modifier: Modifier = Modifier,
    value: Int,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable { onDecrement(value - 1) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_minus),
                contentDescription = "Minus icon",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = value.toString(),
            style = MaterialTheme.typography.labelMedium
                .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Box(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .clickable { onIncrement(value + 1) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(8.dp),
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "Plus icon",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
private fun Preview() {
    HorizontalVegoCounter(value = 1, onIncrement = { /*TODO*/ }) {
        
    }
}