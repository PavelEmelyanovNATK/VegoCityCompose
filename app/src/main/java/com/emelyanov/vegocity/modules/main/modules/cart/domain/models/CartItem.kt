package com.emelyanov.vegocity.modules.main.modules.cart.domain.models

data class CartItem(
    val id: String,
    val title: String,
    val price: Int,
    val count: Int
)