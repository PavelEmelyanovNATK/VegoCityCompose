package com.emelyanov.vegocity.shared.domain.services.cartrepo

import com.emelyanov.vegocity.shared.domain.models.CartItem
import com.emelyanov.vegocity.shared.domain.services.localstorage.ILocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartRepository(
    private val cartStorage: ILocalStorage
) : ICartRepository {
    private val cartList: List<CartItem> get() = cartStorage.getCartList().map { CartItem(it.id, it.count) }
    private val _cartFlow = MutableStateFlow(cartList)
    override val cartFlow: StateFlow<List<CartItem>>
        get() = _cartFlow

    override fun add(productId: String, count: Int) {
        cartStorage.addToCart(productId, count)
        _cartFlow.value = cartList
    }

    override fun remove(productId: String) {
        cartStorage.removeFromCart(productId)
        _cartFlow.value = cartList
    }

    override fun removeAll(productId: String) {
        cartStorage.removeAllFromCart(productId)
        _cartFlow.value = cartList
    }

    override fun removeAll() {
        cartStorage.removeAllFromCart()
        _cartFlow.value = cartList
    }

}