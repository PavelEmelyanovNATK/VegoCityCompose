package com.emelyanov.vegocity.modules.main.modules.cart.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.emelyanov.vegocity.modules.main.modules.cart.domain.CartViewModel
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.CartDismissValue
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.CartItem
import com.emelyanov.vegocity.modules.main.modules.cart.presentation.components.rememberCartDismissState
import com.emelyanov.vegocity.shared.presentation.components.ErrorStateView
import com.example.compose.smallPadding

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ColumnScope.ModalCartDialog(
    viewState: CartViewModel.ViewState
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

                    if(viewState is CartViewModel.ViewState.Presentation) {
                        DeleteAllButton {
                            viewState.onDeleteAll()
                        }
                    }
                }
            }
        }

        when(viewState) {
            is CartViewModel.ViewState.Presentation -> {
                viewState.products.forEach { item ->
                    item(
                        key = item.id
                    ) {
                        CartItem(
                            modifier = Modifier
                                .padding(horizontal = 18.dp)
                                .animateItemPlacement(),
                            title = item.title,
                            imageUrl = item.imageUrl,
                            price = item.price,
                            count = item.count,
                            onDismiss = { viewState.onDelete(item.id) },
                            onIncrement = { viewState.onIncrement(item.id) },
                            onDecrement = { viewState.onDecrement(item.id) }
                        )
                    }
                }

                item(key = "Total") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Итого: ${viewState.totalCost} руб",
                            style = MaterialTheme.typography.labelLarge
                                .copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.End
                                )
                        )

                        Button(
                            onClick = viewState.onGoToOrder,
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

            is CartViewModel.ViewState.Error -> {
                item(
                    key = "Error"
                ) {
                    ErrorStateView(
                        modifier = Modifier.height(400.dp),
                        message = viewState.message,
                        onRefresh = {  }
                    )
                }
            }

            is CartViewModel.ViewState.Loading -> {
                item {
                    Box(Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
private fun BoxScope.DeleteAllButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(horizontal = 18.dp)
            .width(56.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
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
