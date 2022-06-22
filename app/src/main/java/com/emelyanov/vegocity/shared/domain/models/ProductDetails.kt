package com.emelyanov.vegocity.shared.domain.models

import com.emelyanov.vegocity.shared.domain.models.response.PhotoResponse
import kotlinx.serialization.SerialName

data class ProductDetails(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val photoUrls: List<String>,
)