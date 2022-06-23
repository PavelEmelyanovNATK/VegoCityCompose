package com.emelyanov.vegocity.modules.productdetails.domain.usecases

import com.emelyanov.vegocity.modules.productdetails.domain.models.ViewProductDetails
import com.emelyanov.vegocity.modules.productdetails.domain.models.toViewProductDetails
import com.emelyanov.vegocity.shared.domain.models.ProductDetails
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.FavoritesRepository
import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.IFavoritesRepository
import com.emelyanov.vegocity.shared.domain.services.productsrepo.IProductsRepository
import javax.inject.Inject


class GetProductDetailsUseCase
@Inject
constructor(
    private val productsRepository: IProductsRepository,
    private val favoritesRepository: IFavoritesRepository
) {
    suspend operator fun invoke(id: String) : RequestResult<ViewProductDetails> {
        return productsRepository.getProductDetails(id).map {
            it.toViewProductDetails(favoritesRepository.isInFavorites(it.id))
        }
    }
}