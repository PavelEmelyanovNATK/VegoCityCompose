package com.emelyanov.vegocity.shared.domain.models.storage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItemEntity(
    @SerialName("id")
    val id: String,
    @SerialName("count")
    var count: Int,
)