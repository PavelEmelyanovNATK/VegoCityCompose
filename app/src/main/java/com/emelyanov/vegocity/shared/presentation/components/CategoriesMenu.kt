package com.emelyanov.vegocity.shared.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Category
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CategoriesMenu(
    modifier: Modifier = Modifier,
    categories: List<Category> = listOf(),
    onCategoryClick: (String) -> Unit = {},
    isLoading: Boolean
) {
    Column(
        modifier = modifier.padding(18.dp)
    ) {
        Text(
            text = "Категории",
            style = MaterialTheme.typography.bodyLarge
                .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(Modifier.height(10.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = 10.dp,
            crossAxisSpacing = 10.dp
        ) {
            if(isLoading) {
                repeat(3) {
                    key(it) {
                        VegoChip(
                            modifier = Modifier
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp),
                                    highlight = PlaceholderHighlight.shimmer(Color.White)
                                ),
                            isChecked = false,
                            onClick = { },
                            text = "placeho"
                        )
                    }
                }
            } else {
                categories.forEach {
                    key(it.id) {
                        VegoChip(
                            isChecked = it.isSelected,
                            onClick = {
                                onCategoryClick(it.id)
                            },
                            text = it.name
                        )
                    }
                }
            }
        }
    }
}