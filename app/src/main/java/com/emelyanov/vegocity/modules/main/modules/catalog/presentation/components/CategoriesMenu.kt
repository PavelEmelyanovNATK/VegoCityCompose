package com.emelyanov.vegocity.modules.main.modules.catalog.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.shared.presentation.components.VegoChip
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CategoriesMenu(
    modifier: Modifier = Modifier,
    categories: List<Any>,
) {
    Column(
        modifier = modifier.padding(18.dp)
    ) {
        Text(
            text = "Категории",
            style = MaterialTheme.typography.bodyLarge
                .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp
        ) {
            categories.forEach {
                key(it) {
                    var checked by remember { mutableStateOf(false) }

                    VegoChip(
                        isChecked = checked,
                        onClick = { checked = !checked },
                        text = "Chip"
                    )
                }
            }
        }
    }
}