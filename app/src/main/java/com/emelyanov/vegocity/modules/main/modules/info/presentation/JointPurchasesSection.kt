package com.emelyanov.vegocity.modules.main.modules.info.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun JointPurchasesSection() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Информация о совместных покупках"
        )
    }
}