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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R

const val TOOL_BAR_HEIGHT = 60

@Composable
fun DropdownToolBar(
    modifier: Modifier = Modifier,
    title: String,
    isExpanded: () -> Boolean,
    expandProgress: () -> Float,
    onButtonClick: () -> Unit,
) {
    val expandState = if(!isExpanded()) 1f - expandProgress() else expandProgress()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(TOOL_BAR_HEIGHT.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
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
                        .rotate(180 * expandProgress())
                        .alpha(expandState),
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "Menu button",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Icon(
                    modifier = Modifier
                        .rotate( -360 * expandProgress())
                        .alpha(1 - expandState),
                    painter = painterResource(id = R.drawable.ic_expand),
                    contentDescription = "Menu button",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }


            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
                    .copy(color = MaterialTheme.colorScheme.onSecondaryContainer)
            )
        }
    }
}