package com.emelyanov.vegocity.shared.domain.services.favoritesrepo

import com.emelyanov.vegocity.shared.domain.models.FavoriteItem
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.storage.FavoriteItemEntity

interface IFavoritesRepository {
    suspend fun getFavorites(searchField: String = "", categories: List<Int> = listOf()): RequestResult<List<FavoriteItem>>
    suspend fun add(productId: String)
    fun remove(productId: String)
    fun isInFavorites(productId: String): Boolean
}