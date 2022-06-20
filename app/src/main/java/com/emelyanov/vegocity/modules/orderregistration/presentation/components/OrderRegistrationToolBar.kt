package com.emelyanov.vegocity.modules.orderregistration.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.shared.utils.TOOL_BAR_HEIGHT

@Composable
fun OrderRegistrationToolBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TOOL_BAR_HEIGHT.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(18.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(false, 24.dp),
                    onClick = onBackClick
                ),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = "Back button",
            tint = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Оформление заказа",
            style = MaterialTheme.typography.titleLarge
                .copy(color = MaterialTheme.colorScheme.onBackground)
        )
    }
}