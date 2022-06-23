package com.emelyanov.vegocity.shared.domain.services.localstorage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.emelyanov.vegocity.shared.domain.models.storage.CartItemEntity
import com.emelyanov.vegocity.shared.domain.models.storage.FavoriteItemEntity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val CART_STORAGE_KEY = "cartStorageKey"
private const val CART_LIST_KEY = "cartListKey"
private const val FAVORITES_LIST_KEY = "favoritesListKey"

class CartStorage(
    context: Context
) : ILocalStorage {
    private val storage = context.getSharedPreferences(CART_STORAGE_KEY, MODE_PRIVATE)
    private val listCart: MutableList<CartItemEntity>
        get() = storage.getString(CART_LIST_KEY, null)?.let {
            return try {
                Json.decodeFromString(it)
            } catch (ex: Exception) {
                storage.edit().putString(CART_LIST_KEY, "[]").apply()
                mutableListOf()
            }
        } ?: mutableListOf()

    private val listFavorites: MutableList<FavoriteItemEntity>
        get() = storage.getString(FAVORITES_LIST_KEY, null)?.let {
            return try {
                Json.decodeFromString(it)
            } catch (ex: Exception) {
                storage.edit().putString(FAVORITES_LIST_KEY, "[]").apply()
                mutableListOf()
            }
        } ?: mutableListOf()

    override fun getCartList(): List<CartItemEntity> = listCart

    override fun addToCart(productId: String, count: Int) {
        val newList = listCart.apply {
            val item = find { it.id == productId }
            if(item == null)
                add(CartItemEntity(productId, count))
            else
                item.count += count
        }
        saveCart(newList)
    }

    override fun removeFromCart(productId: String) {
        val newList = listCart
        val item = newList.find { it.id == productId } ?: return
        if(item.count == 1) newList.remove(item)
        else item.count--
        saveCart(newList)
    }

    override fun removeAllFromCart(productId: String) {
        val newList = listCart.apply {
            removeAll { it.id == productId }
        }

        saveCart(newList)
    }

    override fun removeAllFromCart() {
        val newList = listCart.apply {
            clear()
        }

        saveCart(newList)
    }

    override fun getFavoritesList(): List<FavoriteItemEntity> = listFavorites

    override fun addToFavorites(product: FavoriteItemEntity) {
        val newList = listFavorites.apply {
            if(any { it.id == product.id }) return@apply

            add(product)
        }

        saveFavorites(newList)
    }

    override fun removeFromFavorites(productId: String) {
        val newList = listFavorites.apply {
            val p = find { it.id == productId } ?: return@apply
            remove(p)
        }

        saveFavorites(newList)
    }

    override fun changeFavoriteTitle(productId: String, title: String) {
        val newList = listFavorites.apply {
            val p = find { it.id == productId } ?: return@apply
            p.title = title
        }

        saveFavorites(newList)
    }

    private fun saveCart(list: List<CartItemEntity>) {
        val listString = Json.encodeToString(list)
        storage.edit().putString(CART_LIST_KEY, listString).apply()
    }

    private fun saveFavorites(list: List<FavoriteItemEntity>) {
        val listString = Json.encodeToString(list)
        storage.edit().putString(FAVORITES_LIST_KEY, listString).apply()
    }
}