package com.emelyanov.vegocity.modules.main.modules.info.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.shared.domain.utils.TOOL_BAR_HEIGHT

@Composable
fun DropdownToolBar(
    modifier: Modifier = Modifier,
    title: String,
    isExpanded: () -> Boolean,
    expandProgress: () -> Float,
    onButtonClick: () -> Unit,
) {
    val expandState = if(!isExpanded()) 1f - expandProgress() else expandProgress()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TOOL_BAR_HEIGHT.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(18.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(radius = 24.dp, bounded = false),
                    onClick = onButtonClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = expandState
                        rotationZ = 180 * expandProgress()
                    },
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu button",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Icon(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = 1 - expandState
                        rotationZ = -360 * expandProgress()
                    },
                painter = painterResource(id = R.drawable.ic_expand),
                contentDescription = "Menu button",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
                .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
}