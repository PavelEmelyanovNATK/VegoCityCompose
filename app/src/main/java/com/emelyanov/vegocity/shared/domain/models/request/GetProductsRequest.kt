package com.emelyanov.vegocity.shared.domain.models.request

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GetProductsRequest(
    @SerialName("categoriesIds")
    val categories: List<Int> = listOf(),
    @SerialName("filter")
    val filter: String = "",
    @SerialName("pagesCount")
    val pagesCount: Int = 1
)
