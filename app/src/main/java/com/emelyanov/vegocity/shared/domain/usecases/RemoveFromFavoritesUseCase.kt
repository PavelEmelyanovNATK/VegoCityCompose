package com.emelyanov.vegocity.shared.domain.usecases

import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.IFavoritesRepository
import javax.inject.Inject


class RemoveFromFavoritesUseCase
@Inject
constructor(
    private val favoritesRepository: IFavoritesRepository
) {
    operator fun invoke(productId: String) {
        favoritesRepository.remove(productId)
    }
}