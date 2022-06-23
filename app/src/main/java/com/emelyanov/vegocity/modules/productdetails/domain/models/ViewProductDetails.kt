package com.emelyanov.vegocity.modules.productdetails.domain.models

import com.emelyanov.vegocity.shared.domain.models.ProductDetails

data class ViewProductDetails (
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val photoUrls: List<String>,
    val isFavorite: Boolean
)

fun ProductDetails.toViewProductDetails(isFavorite: Boolean)
= ViewProductDetails (
    id = id,
    title = title,
    description = description,
    price = price,
    photoUrls = photoUrls,
    isFavorite = isFavorite
)