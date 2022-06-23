package com.emelyanov.vegocity.shared.domain.models.storage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteItemEntity(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    var title: String
)