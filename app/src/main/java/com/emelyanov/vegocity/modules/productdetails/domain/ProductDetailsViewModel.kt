package com.emelyanov.vegocity.modules.productdetails.domain

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.emelyanov.vegocity.modules.main.modules.favorites.domain.FavoritesViewModel
import com.emelyanov.vegocity.modules.productdetails.domain.usecases.AddToCartUseCase
import com.emelyanov.vegocity.modules.productdetails.domain.usecases.AddToFavoritesUseCase
import com.emelyanov.vegocity.modules.productdetails.domain.usecases.CheckIsFavoriteUseCase
import com.emelyanov.vegocity.modules.productdetails.domain.usecases.GetProductDetailsUseCase
import com.emelyanov.vegocity.shared.domain.BaseStateViewModel
import com.emelyanov.vegocity.shared.domain.models.ProductDetails
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.usecases.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel
@Inject
constructor(
    private val getProductDetails: GetProductDetailsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val addToFavorites: AddToFavoritesUseCase,
    private val removeFromFavorites: RemoveFromFavoritesUseCase,
    private val isFavorite: CheckIsFavoriteUseCase
) : BaseStateViewModel<ProductDetailsViewModel.ViewState>(
    initialState = ViewState.Loading
) {
    fun loadInfo(id: String) {
        updateState {
            ViewState.Loading
        }

        viewModelScope.launch {
            val productDetails = getProductDetails(id).checkForError() ?: return@launch

            updateState {
                presentationState(
                    id = productDetails.id,
                    title = productDetails.title,
                    isFavorite = productDetails.isFavorite,
                    photosUrls = productDetails.photoUrls,
                    description = productDetails.description,
                    price = productDetails.price.toInt(),
                    totalCount = 1
                )
            }
        }
    }

    private fun onCountChange(newCount: Int) {
        if(newCount < 1) return

        updateState { oldState ->
            if(oldState !is ViewState.Presentation) return@updateState null

            oldState.copy(
                totalCount = newCount
            )
        }
    }

    private fun onAddToCartClick() {
        val curState = viewState.value
        if(curState !is ViewState.Presentation) return

        addToCartUseCase(curState.id, curState.totalCount)
    }

    private fun onAddToFavoritesClick() {
        viewModelScope.launch {
            updateState { oldState ->
                if(oldState !is ViewState.Presentation) return@updateState null
                if(isFavorite(oldState.id)) {
                    removeFromFavorites(oldState.id)
                } else {
                    addToFavorites(oldState.id)
                }

                oldState.copy(
                    isFavorite = isFavorite(oldState.id)
                )
            }
        }
    }

    private fun <T> RequestResult<T>.checkForError(): T? {
        if(this is RequestResult.Error) {
            updateState {
                ViewState.Error(
                    message = message ?: ""
                )
            }
            return null
        } else return getSuccessResult().data
    }

    private fun presentationState(
        id: String,
        title: String,
        isFavorite: Boolean,
        photosUrls: List<String>,
        description: String,
        price: Int,
        totalCount: Int
    )
    = ViewState.Presentation (
        id = id,
        title = title,
        isFavorite = isFavorite,
        photosUrls = photosUrls,
        description = description,
        price = price,
        totalCount = totalCount,
        onCountChange = ::onCountChange,
        onAddToCart = ::onAddToCartClick,
        onAddToFavorites = ::onAddToFavoritesClick
    )

    sealed interface ViewState {
        object Loading : ViewState
        data class Presentation(
            val id: String,
            val title: String,
            val isFavorite: Boolean,
            val photosUrls: List<String>,
            val description: String,
            val price: Int,
            val totalCount: Int,
            val onCountChange: (Int) -> Unit,
            val onAddToCart: () -> Unit,
            val onAddToFavorites: () -> Unit
        ) : ViewState
        data class Error(val message: String) : ViewState
    }
}