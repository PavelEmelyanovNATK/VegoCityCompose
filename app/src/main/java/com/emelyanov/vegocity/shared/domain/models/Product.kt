package com.emelyanov.vegocity.shared.domain.models

import kotlinx.serialization.SerialName

data class Product(
    val id: String,
    val title: String,
    val category: String,
    val price: Double,
    val photoUrl: String
)