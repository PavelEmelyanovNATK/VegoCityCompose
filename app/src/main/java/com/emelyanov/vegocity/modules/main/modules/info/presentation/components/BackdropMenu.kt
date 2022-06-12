package com.emelyanov.vegocity.modules.main.modules.info.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun BackdropMenu(
    modifier: Modifier = Modifier,
    items: Set<String>,
    itemIcon: @Composable (String) -> Painter,
    itemIconDescription: (String) -> String,
    selectedItem: String,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items.forEach {
            item {
                BackdropMenuItem(
                    title = it,
                    onClick = { onItemClick(it) },
                    isSelected = { it == selectedItem },
                    iconPainter = itemIcon(it),
                    iconContentDescription = itemIconDescription(it)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun BackdropMenuItem(
    title: String,
    onClick: () -> Unit,
    isSelected: () -> Boolean,
    iconPainter: Painter,
    iconContentDescription: String
) {
    val selectionColor = if(isSelected()) MaterialTheme.colorScheme.secondary else Color.Transparent
    val titleColor = if(isSelected()) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        color = selectionColor,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = iconContentDescription,
                tint = titleColor
            )

            Spacer(Modifier.width(18.dp))

            Text(
                modifier = Modifier
                    .weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium
                    .copy(color = titleColor)
            )
        }

    }
}