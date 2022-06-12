package com.emelyanov.vegocity.shared.presentation.components

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun VegoTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(16.dp),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
        .copy(color = MaterialTheme.colorScheme.onSurface),
) {
    var isInFocus by remember { mutableStateOf(false) }
    trailingIcon?.let{ it() }

    BasicTextField(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .padding(contentPadding)
            .onFocusChanged {
                isInFocus = it.isFocused
            },
        value = value,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                leadingIcon?.let{ it() }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    innerTextField()
                    if(placeholder != null && !isInFocus && value.isEmpty()){
                        placeholder()
                    }
                }

                trailingIcon?.let{ it() }
            }

        },
        singleLine = singleLine,
        maxLines = maxLines,
        textStyle = textStyle
    )
}
