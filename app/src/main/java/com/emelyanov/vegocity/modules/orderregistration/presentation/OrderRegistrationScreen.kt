package com.emelyanov.vegocity.modules.orderregistration.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emelyanov.vegocity.R
import com.emelyanov.vegocity.modules.orderregistration.domain.OrderRegistrationViewModel
import com.emelyanov.vegocity.modules.orderregistration.presentation.components.OrderRegistrationToolBar
import com.emelyanov.vegocity.shared.presentation.components.VegoTextField
import com.example.compose.smallPadding

@Composable
fun OrderRegistrationScreen(
    viewState: OrderRegistrationViewModel.ViewState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(end = 18.dp)
    ) {
        OrderRegistrationToolBar(
            onBackClick = onBackClick
        )

        if(viewState is OrderRegistrationViewModel.ViewState.Presentation) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(18.dp),
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = "Person icon"
                )

                val fullName = viewState.fullNameField.collectAsState()
                OrderTextField(
                    modifier = Modifier.weight(1f),
                    value = fullName.value,
                    onValueChange = viewState.onFullNameChange,
                    placeholder = "ФИО"
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(18.dp),
                    painter = painterResource(R.drawable.ic_phone),
                    contentDescription = "Phone icon"
                )

                val phone = viewState.phoneField.collectAsState()
                OrderTextField(
                    modifier = Modifier.weight(1f),
                    value = phone.value,
                    onValueChange = viewState.onPhoneChange,
                    placeholder = "Телефон"
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(18.dp),
                    painter = painterResource(R.drawable.ic_map_pin),
                    contentDescription = "Address icon"
                )

                val address = viewState.addressField.collectAsState()
                OrderTextField(
                    modifier = Modifier.weight(1f),
                    value = address.value,
                    onValueChange = viewState.onAddressChange,
                    placeholder = "Адрес"
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(18.dp),
                    painter = painterResource(R.drawable.ic_comment),
                    contentDescription = "Chat icon"
                )

                val comment = viewState.commentField.collectAsState()
                OrderTextField(
                    modifier = Modifier.weight(1f),
                    value = comment.value,
                    onValueChange = viewState.onCommentChange,
                    placeholder = "Комментарий",
                    singleLine = false,
                    maxLines = 10
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    onClick = { },
                    contentPadding = ButtonDefaults.smallPadding
                ) {
                    Text(
                        text = "Оформить",
                        style = MaterialTheme.typography.labelLarge
                            .copy(MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    maxLines: Int = 1,
    singleLine: Boolean = true
) = VegoTextField(
    modifier = modifier
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = RoundedCornerShape(8.dp)
        ),
    value = value,
    onValueChange = onValueChange,
    shape = RoundedCornerShape(8.dp),
    textStyle = MaterialTheme.typography.bodyLarge
        .copy(color = MaterialTheme.colorScheme.onSurface),
    placeholder = {
        androidx.compose.material.Text(
            text = placeholder,
            style = MaterialTheme.typography.bodyLarge
                .copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
    },
    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
    singleLine = singleLine,
    maxLines =maxLines,
)
@Composable
@Preview
private fun Preview() {
    MaterialTheme {
        //OrderRegistrationScreen()
    }
}