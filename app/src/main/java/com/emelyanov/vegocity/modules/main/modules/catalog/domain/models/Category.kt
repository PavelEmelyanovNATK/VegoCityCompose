package com.emelyanov.vegocity.modules.main.modules.catalog.domain.models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Category(
    val id: String,
    val name: String,
    val isSelected: Boolean = false
)