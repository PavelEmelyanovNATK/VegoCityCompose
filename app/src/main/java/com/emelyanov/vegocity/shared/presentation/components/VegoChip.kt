package com.emelyanov.vegocity.shared.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R

@ExperimentalAnimationApi
@Composable
fun VegoChip(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    isChecked: Boolean,
    checkedBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    uncheckedBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    checkedForegroundColor: Color = MaterialTheme.colorScheme.onSurface,
    uncheckedForegroundColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    onClick: () -> Unit,
    text: String
) {
    val borderColor = MaterialTheme.colorScheme.outline

    val pxBorder = with(LocalDensity.current) { 2.dp.toPx() }
    val border = animateFloatAsState(targetValue = if(!isChecked) pxBorder else 0f)

    val currentBorderColor = remember(border) {
        derivedStateOf {
            if(border.value > 0) borderColor else Color.Transparent
        }
    }


    val backgroundColor = animateColorAsState(
        targetValue = if(isChecked)
            checkedBackgroundColor
        else
            uncheckedBackgroundColor,
        animationSpec = spring()
    )

    val textColor = animateColorAsState(
        targetValue = if(isChecked)
            checkedForegroundColor
        else
            uncheckedForegroundColor,
        animationSpec = spring()
    )

    val localDensity = LocalDensity.current

    Row(
        modifier = modifier
            .clip(shape)
            .clickable { onClick() }
            .drawBehind {
                drawRect(color = backgroundColor.value)
                drawOutline(
                    shape.createOutline(size, layoutDirection, localDensity),
                    color = currentBorderColor.value,
                    style = Stroke(width = border.value),
                )
            }
            .padding(vertical = 6.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.height(18.dp))
        AnimatedVisibility(
            visible = isChecked,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Check icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium
                .copy(color = textColor.value)
        )
    }
}