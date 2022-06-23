package com.emelyanov.vegocity.modules.productdetails.domain.usecases

import com.emelyanov.vegocity.shared.domain.services.cartrepo.CartRepository
import com.emelyanov.vegocity.shared.domain.services.cartrepo.ICartRepository
import javax.inject.Inject


class AddToCartUseCase
@Inject
constructor(
    private val cartRepository: ICartRepository
) {
    operator fun invoke(id: String, count: Int) {
        cartRepository.add(id, count)
    }
}