package com.emelyanov.vegocity.shared.domain.models.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ProductResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("category")
    val category: String,
    @SerialName("price")
    val price: Double,
    @SerialName("imagePath")
    val photoPath: String?
)