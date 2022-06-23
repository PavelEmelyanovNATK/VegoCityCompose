package com.emelyanov.vegocity.modules.main.modules.favorites.domain.usecases

import com.emelyanov.vegocity.modules.main.modules.favorites.domain.models.ViewFavorite
import com.emelyanov.vegocity.shared.domain.models.FavoriteItem
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.view.ViewCategory
import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.IFavoritesRepository
import javax.inject.Inject


class GetFavoritesUseCase
@Inject
constructor(
    private val favoritesRepository: IFavoritesRepository
) {
    suspend operator fun invoke(searchFilter: String = "", categories: List<ViewCategory> = listOf()) : RequestResult<List<ViewFavorite>> {
        return favoritesRepository.getFavorites(
            searchField = searchFilter,
            categories = categories.map { it.id.toInt() }
        ).map { list ->
            list.filterIsInstance<FavoriteItem.Normal>()
                .map {
                    ViewFavorite(
                        id = it.id,
                        title = it.title,
                        photoUrl = it.photoUrl,
                        category = it.category,
                        price = it.price.toInt()
                    )
                }
        }
    }
}