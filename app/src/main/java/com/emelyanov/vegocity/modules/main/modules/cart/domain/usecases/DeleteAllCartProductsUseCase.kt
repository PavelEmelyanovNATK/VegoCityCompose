package com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases

import javax.inject.Inject


class DeleteAllCartProductsUseCase
@Inject
constructor(

) {
    operator fun invoke() {
        cartItems.clear()
    }
}