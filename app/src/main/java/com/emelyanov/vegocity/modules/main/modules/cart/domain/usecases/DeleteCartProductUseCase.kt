package com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases

import javax.inject.Inject


class DeleteCartProductUseCase
@Inject
constructor(

) {
    operator fun invoke(id: String) {
        cartItems.find { it.id == id }?.let {
            cartItems.remove(it)
        }
    }
}