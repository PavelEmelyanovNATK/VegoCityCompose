package com.emelyanov.vegocity.modules.main.modules.cart.domain

import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import com.emelyanov.vegocity.modules.main.modules.cart.domain.models.ViewCartItem
import com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases.*
import com.emelyanov.vegocity.shared.domain.BaseStateViewModel
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel
@Inject
constructor(
    private val getCartProducts: GetCartProductsUseCase,
    private val getRawCartProducts: GetRawCartProductsUseCase,
    private val decrementItemCount: DecrementItemCountUseCase,
    private val incrementItemCount: IncrementItemCountUseCase,
    private val deleteAllProductsUseCase: DeleteAllCartProductsUseCase,
    private val navigateToOrder: NavigateToOrderUseCase
) : BaseStateViewModel<CartViewModel.ViewState>(
    initialState = ViewState.Loading
) {
    fun close() {
        updateState {
            ViewState.Empty
        }
    }

    fun open() {
        updateState {
            ViewState.Loading
        }

        viewModelScope.launch {
            updateState {
                val products = getCartProducts().checkForError() ?: return@launch
                presentationState(products, products.sumOf { it.price * it.count })
            }
        }
    }

    private fun onDeleteAllClick() {
        deleteAllProductsUseCase.deleteAll()
        updateState {
            ViewState.Empty
        }
    }

    private fun onDeleteClick(id: String) {
        updateState { oldState ->
            if (oldState !is ViewState.Presentation) return@updateState null

            deleteAllProductsUseCase.deleteAll(id)
            rawUpdate(oldState)
        }
    }

//    private fun onCountChange(id: String, newCount: Int) {
//        updateState { oldState ->
//            if(oldState is ViewState.Presentation) {
//                oldState.copy(
//                    products = oldState.products.map {
//                        if(it.id == id) it.copy(count = newCount) else it
//                    }
//                )
//            } else {
//                null
//            }
//        }
//    }

    private fun onIncrement(id: String) {
        updateState { oldState ->
            if(oldState !is ViewState.Presentation) return@updateState null
            incrementItemCount(id)
            rawUpdate(oldState)
        }
    }

    private fun onDecrement(id: String) {
        updateState { oldState ->
            if(oldState !is ViewState.Presentation) return@updateState null
            decrementItemCount(id)
            rawUpdate(oldState)
        }
    }

    private fun onGoToOrderClick() {
        navigateToOrder()
    }

    private fun rawUpdate(oldState: ViewState.Presentation): ViewState.Presentation {
        val rawProducts = getRawCartProducts()
        val products = oldState.products.filter { p-> rawProducts.any { it.id == p.id } }
            .map { p ->
                val rp = rawProducts.first { it.id == p.id }
                if(rp.count != p.count)
                    p.copy(count = rp.count)
                else
                    p
            }
        return presentationState(products, products.sumOf { it.price * it.count })
    }

    private fun presentationState(products: List<ViewCartItem>, totalCost: Int)
    = ViewState.Presentation(
        products = products,
        totalCost = totalCost,
        onIncrement = ::onIncrement,
        onDecrement = ::onDecrement,
        onDelete = ::onDeleteClick,
        onDeleteAll = ::onDeleteAllClick,
        onGoToOrder = ::onGoToOrderClick
    )

    private fun <T> RequestResult<T>.checkForError(): T? {
        if(this is RequestResult.Error) {
            updateState { oldState ->
                ViewState.Error(
                    message = message ?: ""
                )
            }
            return null
        } else return getSuccessResult().data
    }

    sealed interface ViewState {
        object Loading : ViewState
        object Empty : ViewState
        data class Presentation(
            val products: List<ViewCartItem>,
            val totalCost: Int,
            val onIncrement: (String) -> Unit,
            val onDecrement: (String) -> Unit,
            val onDelete: (String) -> Unit,
            val onDeleteAll: () -> Unit,
            val onGoToOrder: () -> Unit
        ) : ViewState
        data class Error(val message: String) : ViewState
    }
}