package com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases

import com.emelyanov.vegocity.shared.domain.services.cartrepo.CartRepository
import com.emelyanov.vegocity.shared.domain.services.cartrepo.ICartRepository
import javax.inject.Inject


class DeleteAllCartProductsUseCase
@Inject
constructor(
    private val cartRepository: ICartRepository
) {
    fun deleteAll() {
        cartRepository.removeAll()
    }

    fun deleteAll(productId: String) {
        cartRepository.removeAll(productId)
    }
}