package com.emelyanov.vegocity.shared.domain.services.favoritesrepo

import com.emelyanov.vegocity.shared.domain.models.FavoriteItem
import com.emelyanov.vegocity.shared.domain.models.RequestErrorType
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.storage.FavoriteItemEntity
import com.emelyanov.vegocity.shared.domain.services.localstorage.ILocalStorage
import com.emelyanov.vegocity.shared.domain.services.vegoapi.IVegoApi
import com.emelyanov.vegocity.shared.domain.utils.vegoRequestWrapper

class FavoritesRepository(
    private val vegoApi: IVegoApi,
    private val localStorage: ILocalStorage
) :IFavoritesRepository {
    override suspend fun getFavorites(
        searchField: String,
        categories: List<Int>
    ): RequestResult<List<FavoriteItem>> {
        val localFavorites = localStorage.getFavoritesList()

        val favorites = mutableListOf<FavoriteItem>()

        for(item in localFavorites) {
            val productResult = vegoRequestWrapper { vegoApi.fetchProduct(item.id) }
            if(productResult is RequestResult.Error && productResult.type == RequestErrorType.NotFound) {
                favorites.add(FavoriteItem.Outdated(item.id, item.title))
            } else if(productResult is RequestResult.Error) {
                return RequestResult.Error(productResult.type, productResult.message, productResult.exception)
            } else {
                val product = productResult.getSuccessResult().data

                if(product.title != item.title) {
                    localStorage.changeFavoriteTitle(product.id, product.title)
                }

                if(!product.title.contains(searchField)) continue
                if(categories.isNotEmpty() && !categories.contains(product.categoryId)) continue

                favorites.add(
                    FavoriteItem.Normal(
                        id = product.id,
                        title = product.title,
                        photoUrl = product.photoPath ?: "",
                        category = product.category,
                        price = product.price,
                    )
                )
            }
        }

        return RequestResult.Success(favorites)
    }

    override suspend fun add(productId: String) {
        val productResult = vegoRequestWrapper { vegoApi.fetchProduct(productId) }
        if(productResult is RequestResult.Error) return

        val product = productResult.getSuccessResult().data
        localStorage.addToFavorites(
            FavoriteItemEntity(
                id = product.id,
                title = product.title
            )
        )
    }

    override fun remove(productId: String) {
        localStorage.removeFromFavorites(productId)
    }

    override fun isInFavorites(productId: String): Boolean = localStorage.getFavoritesList().any { it.id == productId }
}