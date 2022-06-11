package com.emelyanov.vegocity.modules.main.modules.cart.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.shared.presentation.components.SwipeableIndicator
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.CartDismissValue
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.CartItem
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.rememberCartDismissState
import com.example.compose.smallPadding

@ExperimentalMaterialApi
@Composable
fun ColumnScope.ModalCartDialog(
) {
    SwipeableIndicator(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(12.dp)
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 18.dp),
                        text = "Корзина",
                        style = MaterialTheme.typography.titleLarge
                            .copy(color = MaterialTheme.colorScheme.onSurface)
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(horizontal = 18.dp)
                            .width(56.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(3.dp),
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Swipe icon",
                            tint = MaterialTheme.colorScheme.onErrorContainer
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(30.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(3.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_swipe),
                                contentDescription = "Swipe icon",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        repeat(10) {
            item {
                CartItem(
                    modifier = Modifier
                        .padding(horizontal = 18.dp),
                    onDismiss = {}
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Итого: ",
                    style = MaterialTheme.typography.labelLarge
                        .copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.End
                        )
                )

                Button(
                    onClick = { },
                    contentPadding = ButtonDefaults.smallPadding
                ) {
                    Text(
                        text = "Перейти к оформлению",
                        style = MaterialTheme.typography.labelLarge
                            .copy(MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }

            Spacer(Modifier.height(18.dp))
        }
    }
}
