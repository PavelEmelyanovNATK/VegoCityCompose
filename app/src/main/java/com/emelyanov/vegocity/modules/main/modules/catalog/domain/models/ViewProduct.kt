package com.emelyanov.vegocity.modules.main.modules.catalog.domain.models

import com.emelyanov.vegocity.shared.domain.models.Product

data class ViewProduct(
    val id: String,
    val photoUrl: String,
    val title: String,
    val isNew: Boolean,
    val price: Int,
    val actualPrice: Int,
    val category: String,
)

fun Product.toViewProduct()
= ViewProduct(
    id = id,
    photoUrl = photoUrl,
    title = title,
    isNew = false,
    price = price.toInt(),
    actualPrice = price.toInt(),
    category = category
)