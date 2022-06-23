package com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases

import com.emelyanov.vegocity.shared.domain.services.cartrepo.CartRepository
import com.emelyanov.vegocity.shared.domain.services.cartrepo.ICartRepository
import javax.inject.Inject


class DecrementItemCountUseCase
@Inject
constructor(
    private val cartRepository: ICartRepository
) {
    operator fun invoke(id: String) {
        cartRepository.remove(id)
    }
}