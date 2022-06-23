package com.emelyanov.vegocity.modules.productdetails.domain.usecases

import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.IFavoritesRepository
import javax.inject.Inject


class AddToFavoritesUseCase
@Inject
constructor(
    private val favoritesRepository: IFavoritesRepository
) {
    suspend operator fun invoke(productId: String) {
        favoritesRepository.add(productId)
    }
}