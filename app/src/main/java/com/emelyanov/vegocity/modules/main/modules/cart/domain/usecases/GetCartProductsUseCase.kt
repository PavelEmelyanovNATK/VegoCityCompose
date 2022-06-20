package com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases

import com.emelyanov.vegocity.modules.main.modules.cart.domain.models.CartItem
import javax.inject.Inject

val cartItems = mutableListOf(
    CartItem(
        id = "p1",
        title = "Product 1",
        price = 1999,
        count = 1
    ),
    CartItem(
        id = "p2",
        title = "Product 2",
        price = 1999,
        count = 1
    ),
    CartItem(
        id = "p3",
        title = "Product 3",
        price = 1999,
        count = 1
    ),
    CartItem(
        id = "p4",
        title = "Product 4",
        price = 1999,
        count = 1
    ),
    CartItem(
        id = "p5",
        title = "Product 5",
        price = 1999,
        count = 1
    ),
)


class GetCartProductsUseCase
@Inject
constructor(

) {
    operator fun invoke() : List<CartItem> {
        return cartItems
    }
}