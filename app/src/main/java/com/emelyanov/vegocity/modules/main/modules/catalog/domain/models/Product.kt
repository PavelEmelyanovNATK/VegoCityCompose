package com.emelyanov.vegocity.modules.main.modules.catalog.domain.models

data class Product(
    val id: String,
    val photoRef: String,
    val title: String,
    val isNew: Boolean,
    val price: Int,
    val actualPrice: Int,
    val categoryId: String,
)