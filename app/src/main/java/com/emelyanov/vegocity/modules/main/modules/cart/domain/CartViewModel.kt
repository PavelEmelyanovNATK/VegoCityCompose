package com.emelyanov.vegocity.modules.main.modules.cart.domain

import com.emelyanov.vegocity.modules.main.modules.cart.domain.models.CartItem
import com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases.DeleteAllCartProductsUseCase
import com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases.DeleteCartProductUseCase
import com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases.GetCartProductsUseCase
import com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases.NavigateToOrderUseCase
import com.emelyanov.vegocity.shared.domain.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel
@Inject
constructor(
    private val getCartProducts: GetCartProductsUseCase,
    private val deleteCartProduct: DeleteCartProductUseCase,
    private val deleteAllProducts: DeleteAllCartProductsUseCase,
    private val navigateToOrder: NavigateToOrderUseCase
) : BaseStateViewModel<CartViewModel.ViewState>(
    initialState = ViewState.Loading
) {

    init {
        updateState {
            ViewState.Loading
        }
        updateState {
            val products = getCartProducts()
            presentationState(products, products.sumOf { it.price })
        }
    }

    private fun onDeleteAllClick() {
        deleteAllProducts()
        updateState {
            val products = getCartProducts()
            presentationState(products, products.sumOf { it.price })
        }
    }

    private fun onDeleteClick(id: String) {
        deleteCartProduct(id)

        updateState {
            val products = getCartProducts()
            presentationState(products, products.sumOf { it.price })
        }
    }

    private fun onCountChange(id: String, newCount: Int) {
        if(newCount < 0) {
            onDeleteClick(id)
            return
        }

        updateState { oldState ->
            if(oldState is ViewState.Presentation) {
                oldState.copy(
                    products = oldState.products.map {
                        if(it.id == id) it.copy(count = newCount) else it
                    }
                )
            } else {
                null
            }
        }
    }

    private fun onGoToOrderClick() {
        navigateToOrder()
    }

    private fun presentationState(products: List<CartItem>, totalCost: Int)
    = ViewState.Presentation(
        products = products,
        totalCost = totalCost,
        onCountChange = ::onCountChange,
        onDelete = ::onDeleteClick,
        onDeleteAll = ::onDeleteAllClick,
        onGoToOrder = ::onGoToOrderClick
    )

    sealed interface ViewState {
        object Loading : ViewState
        object Empty : ViewState
        data class Presentation(
            val products: List<CartItem>,
            val totalCost: Int,
            val onCountChange: (String, Int) -> Unit,
            val onDelete: (String) -> Unit,
            val onDeleteAll: () -> Unit,
            val onGoToOrder: () -> Unit
        ) : ViewState
        data class Error(val message: String) : ViewState
    }
}