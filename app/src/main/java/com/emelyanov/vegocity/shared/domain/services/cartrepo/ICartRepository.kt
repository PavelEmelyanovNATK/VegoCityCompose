package com.emelyanov.vegocity.shared.domain.services.cartrepo

import com.emelyanov.vegocity.shared.domain.models.CartItem
import com.emelyanov.vegocity.shared.domain.models.storage.CartItemEntity
import kotlinx.coroutines.flow.StateFlow

interface ICartRepository {
    val cartFlow: StateFlow<List<CartItem>>

    fun add(productId: String, count: Int = 1)
    fun remove(productId: String)
    fun removeAll(productId: String)
    fun removeAll()
}