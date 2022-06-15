package com.emelyanov.vegocity.shared.presentation.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Category
import com.google.accompanist.flowlayout.FlowRow

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CategoriesMenu(
    modifier: Modifier = Modifier,
    categories: List<Category>,
    onCategoryClick: (String) -> Unit
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