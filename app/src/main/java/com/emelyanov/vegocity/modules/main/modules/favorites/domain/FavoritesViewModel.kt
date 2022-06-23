package com.emelyanov.vegocity.modules.main.modules.favorites.domain

import androidx.lifecycle.viewModelScope
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.GroupedProducts
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase.GetProductsUseCase
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase.NavigateToDetailsUseCase
import com.emelyanov.vegocity.modules.main.modules.favorites.domain.models.ViewFavorite
import com.emelyanov.vegocity.modules.main.modules.favorites.domain.usecases.GetFavoritesUseCase
import com.emelyanov.vegocity.shared.domain.BaseStateViewModel
import com.emelyanov.vegocity.shared.domain.models.FavoriteItem
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.view.ViewCategory
import com.emelyanov.vegocity.shared.domain.usecases.GetCategoriesUseCase
import com.emelyanov.vegocity.shared.domain.usecases.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private typealias GroupedFavorites = Map<String, List<ViewFavorite>>

@HiltViewModel
class FavoritesViewModel
@Inject
constructor(
    private val getCategories: GetCategoriesUseCase,
    private val getFavorites: GetFavoritesUseCase,
    private val navigateToDetails: NavigateToDetailsUseCase,
    private val removeFromFavorites: RemoveFromFavoritesUseCase
) : BaseStateViewModel<FavoritesViewModel.ViewState>(
    initialState = ViewState.Loading
) {
    private val _searchField = MutableStateFlow("")
    val searchField: StateFlow<String> = _searchField

    private val waitingMillis = 500
    private var timer: Job? = null

    init {
        reloadToDefault()
    }

    fun reloadToDefault() {
        updateState {
            ViewState.Loading
        }

        viewModelScope.launch {
            try {
                val categories = getCategories().checkForError() ?: return@launch
                val products = getFavorites(searchFilter = searchField.value).checkForError() ?: return@launch
                val groupedProducts = products.groupBy { it.category }

                updateState { oldState ->
                    presentationState(
                        categories = categories,
                        products = groupedProducts
                    )
                }
            } catch (ex: Exception) {
                updateState { oldState ->
                    ViewState.Error(ex.message?:"")
                }
            }
        }
    }

    private fun onProductClick(id: String) = navigateToDetails(id)

    private fun reloadTimer(onComplete: suspend () -> Unit) {
        if(timer != null && timer?.isActive == true) timer?.cancel()

        timer = viewModelScope.launch {
            var currentTimerValue = waitingMillis
            while(currentTimerValue > 0) {
                currentTimerValue -= 100
                delay(100)
            }
            onComplete()
        }
    }

    private suspend fun reloadWithFilter(
        searchField: String
    ) {
        val oldState = _viewState.value
        _viewState.value = ViewState.Loading

        viewModelScope.launch {
            delay(1000)
            if(oldState is ViewState.Error) {
                reloadToDefault()
            } else if(oldState is ViewState.Presentation) {
                if(oldState.categories.any { it.isSelected } || searchField.isNotEmpty()) {
                    updateState {
                        val categories = getCategories().checkForError() ?: return@launch

                        val newCategories = categories.map { curCategory ->
                            val prevCategory = oldState.categories.find { it.id == curCategory.id } ?: return@map curCategory
                            curCategory.copy(isSelected = prevCategory.isSelected)
                        }

                        val products = getFavorites(searchField, newCategories.filter { it.isSelected }).checkForError() ?: return@launch

                        val groupedProducts = products.groupBy { it.category }
                        presentationState(
                            categories = newCategories,
                            products = groupedProducts
                        )
                    }
                } else {
                    reloadToDefault()
                }
            }
        }
    }

    fun searchFiledChanged(text: String) {
        _searchField.value = text

        reloadTimer {
            reloadWithFilter(_searchField.value)
        }
    }

    private fun changeCategoryMark(
        productsViewState: ViewState.Presentation,
        categoryId: String
    ) : ViewState.Presentation? {
        val clickedCategory = productsViewState.categories.find { it.id == categoryId } ?: return null

        val categories = productsViewState.categories

        val newCategories = categories.map { curCategory ->
            val prevCategory = productsViewState.categories.find { it.id == curCategory.id } ?: return@map curCategory
            curCategory.copy(isSelected = if(curCategory.id == clickedCategory.id) !clickedCategory.isSelected else prevCategory.isSelected)
        }

        return productsViewState.copy(
            categories = newCategories
        )
    }

    private fun categoryClicked(categoryId: String) {
        viewModelScope.launch {
            val oldState = _viewState.value
            if(oldState !is ViewState.Presentation) return@launch
            val newProductsState = changeCategoryMark(oldState, categoryId) ?: return@launch
            _viewState.value = newProductsState
            reloadTimer {
                reloadWithFilter(searchField.value)
            }
        }
    }

    private fun onRemoveClick(productId: String) {
        removeFromFavorites(productId)

        viewModelScope.launch {
            reloadWithFilter(searchField.value)
        }
    }

    private fun presentationState(
        products: GroupedFavorites,
        categories: List<ViewCategory>,
    ) = ViewState.Presentation(
        products = products,
        categories = categories,
        onCategoryClick = ::categoryClicked,
        onProductClick = ::onProductClick,
        onRemove = ::onRemoveClick
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
        data class Presentation(
            val products: GroupedFavorites,
            val categories: List<ViewCategory>,
            val onCategoryClick: (String) -> Unit,
            val onProductClick: (String) -> Unit,
            val onRemove: (String) -> Unit,
        ) : ViewState
        data class Error(val message: String) : ViewState
    }
}
