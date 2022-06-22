package com.emelyanov.vegocity.shared.domain.models.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CategoryResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)