package com.emelyanov.vegocity.modules.productdetails.domain.usecases

import com.emelyanov.vegocity.shared.domain.models.ProductDetails
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.services.productsrepo.IProductsRepository
import javax.inject.Inject


class GetProductDetailsUseCase
@Inject
constructor(
    private val productsRepository: IProductsRepository
) {
    suspend operator fun invoke(id: String) : RequestResult<ProductDetails> {
        return productsRepository.getProductDetails(id)
    }
}