package com.emelyanov.vegocity.shared.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.example.compose.smallPadding

@Composable
fun ErrorStateView(
    modifier: Modifier = Modifier,
    message: String,
    onRefresh: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Icon(
                modifier = Modifier.size(90.dp, 80.dp),
                painter = painterResource(R.drawable.ic_error),
                contentDescription = "Error icon",
                tint = MaterialTheme.colorScheme.error
            )

            Text(
                text = message,
                style = MaterialTheme.typography.labelLarge
                    .copy(MaterialTheme.colorScheme.error)
            )

            Button(
                onClick = { onRefresh() },
                contentPadding = ButtonDefaults.smallPadding
            ) {
                Text(
                    text = "Обновить",
                    style = MaterialTheme.typography.labelLarge
                        .copy(MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}