package com.emelyanov.vegocity.shared.domain.services.localstorage

import com.emelyanov.vegocity.shared.domain.models.storage.CartItemEntity
import com.emelyanov.vegocity.shared.domain.models.storage.FavoriteItemEntity

interface ILocalStorage {
    fun getCartList(): List<CartItemEntity>
    fun addToCart(productId: String, count: Int = 1)
    fun removeFromCart(productId: String)
    fun removeAllFromCart(productId: String)
    fun removeAllFromCart()
    fun getFavoritesList(): List<FavoriteItemEntity>
    fun addToFavorites(product: FavoriteItemEntity)
    fun removeFromFavorites(productId: String)
    fun changeFavoriteTitle(productId: String, title: String)
}