package com.emelyanov.vegocity.shared.domain.models.response

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ProductDetailsResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: Double,
    @SerialName("photos")
    val photos: List<PhotoResponse>,
    @SerialName("mainPhotoId")
    val mainPhotoId: String?
)
