package com.emelyanov.vegocity.shared.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListItemCircleIndicator(
    isSelected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.secondary,
    unselectedColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val currentColor = animateColorAsState(
        targetValue = if(isSelected) selectedColor else unselectedColor,
        animationSpec = spring()
    )

    Box(
        modifier = Modifier
            .size(6.dp)
            .clip(CircleShape)
            .background(currentColor.value)
    )
}
