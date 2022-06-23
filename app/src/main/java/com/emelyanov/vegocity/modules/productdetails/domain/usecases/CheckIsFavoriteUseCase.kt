package com.emelyanov.vegocity.modules.productdetails.domain.usecases

import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.IFavoritesRepository
import javax.inject.Inject


class CheckIsFavoriteUseCase
@Inject
constructor(
    private val favoritesRepository: IFavoritesRepository
) {
    operator fun invoke(productId: String): Boolean = favoritesRepository.isInFavorites(productId)
}