package com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases

import com.emelyanov.vegocity.modules.main.modules.cart.domain.models.ViewCartItem
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.services.cartrepo.ICartRepository
import com.emelyanov.vegocity.shared.domain.services.productsrepo.IProductsRepository
import javax.inject.Inject

class GetCartProductsUseCase
@Inject
constructor(
    private val cartRepository: ICartRepository,
    private val productRepository: IProductsRepository
) {
    suspend operator fun invoke() : RequestResult<List<ViewCartItem>> {
        val cartItems = cartRepository.cartFlow.value
        val products = mutableListOf<ViewCartItem>()

        for(item in cartItems) {
            val productInfo = productRepository.getProduct(item.id)
            if (productInfo is RequestResult.Error) return@invoke RequestResult.Error(productInfo.type, productInfo.message, productInfo.exception)

            val product = productInfo.getSuccessResult().data
            products.add(ViewCartItem(product.id, product.title, product.photoUrl, product.price.toInt(), item.count))
        }

        return RequestResult.Success(products)
    }
}