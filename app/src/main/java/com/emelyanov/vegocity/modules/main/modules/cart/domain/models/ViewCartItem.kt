package com.emelyanov.vegocity.modules.main.modules.cart.domain.models

data class ViewCartItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val price: Int,
    val count: Int
)