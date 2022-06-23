package com.emelyanov.vegocity.modules.main.modules.catalog.domain

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.NewProduct
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.ViewProduct
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase.*
import com.emelyanov.vegocity.shared.domain.BaseStateViewModel
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.view.ViewCategory
import com.emelyanov.vegocity.shared.domain.usecases.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias GroupedProducts = Map<String, List<ViewProduct>>

@HiltViewModel
class CatalogViewModel
@Inject
constructor(
    private val getCategories: GetCategoriesUseCase,
    private val getNewProducts: GetNewProductsUseCase,
    private val getProducts: GetProductsUseCase,
    private val navigateToDetails: NavigateToDetailsUseCase
) : BaseStateViewModel<CatalogViewModel.ViewState>(
    ViewState.Default(
        productsViewState = ProductsViewState.Loading,
        newProductsViewState = NewProductsViewState.Loading
    )
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
            viewStateLoading()
        }

        viewModelScope.launch {
            val newProducts = getNewProducts()

            updateState { oldState ->
                if(oldState is ViewState.Default)
                    oldState.copy(
                        newProductsViewState = NewProductsViewState.Presentation(newProducts)
                    )
                else
                    ViewState.Default(
                        newProductsViewState = NewProductsViewState.Presentation(newProducts),
                        productsViewState = ProductsViewState.Loading
                    )
            }
        }

        viewModelScope.launch {
            val categories = getCategories().checkForError() ?: return@launch
            val products = getProducts(searchFilter = searchField.value).checkForError() ?: return@launch
            val groupedProducts = products.groupBy { it.category }

            updateState { oldState ->
                if(oldState is ViewState.Default)
                    oldState.copy(
                        productsViewState = ProductsViewState.Presentation(
                            categories = categories,
                            products = groupedProducts
                        )
                    )
                else
                    ViewState.Default(
                        productsViewState = ProductsViewState.Presentation(
                            categories = categories,
                            products = groupedProducts
                        ),
                        newProductsViewState = NewProductsViewState.Loading
                    )
            }
        }
    }

    fun onProductClick(id: String) = navigateToDetails(id)

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
        productsViewState: ProductsViewState,
        searchField: String
    ) {
        _viewState.value = viewStateLoading()

        viewModelScope.launch {
            if(productsViewState is ProductsViewState.Error) {
                reloadToDefault()
            } else if(productsViewState is ProductsViewState.Presentation) {
                if(productsViewState.categories.any { it.isSelected } || searchField.isNotEmpty()) {
                    updateState {
                        val categories = getCategories().checkForError() ?: return@launch

                        val newCategories = categories.map { curCategory ->
                            val prevCategory = productsViewState.categories.find { it.id == curCategory.id } ?: return@map curCategory
                            curCategory.copy(isSelected = prevCategory.isSelected)
                        }

                        val products = getProducts(newCategories.filter { it.isSelected }, searchField).checkForError() ?: return@launch

                        val groupedProducts = products.groupBy { it.category }
                        ViewState.Filter(
                            productsViewState = ProductsViewState.Presentation(
                                categories = newCategories,
                                products = groupedProducts
                            )
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
            when(val oldState = _viewState.value)  {
                is ViewState.Filter -> {
                    reloadWithFilter(oldState.productsViewState, _searchField.value)
                }
                is ViewState.Default -> {
                    reloadWithFilter(oldState.productsViewState, _searchField.value)
                }
            }
        }
    }

    private fun changeCategoryMark(
        productsViewState: ProductsViewState.Presentation,
        categoryId: String
    ) : ProductsViewState.Presentation? {
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

    fun categoryClicked(categoryId: String) {
        viewModelScope.launch {
            when (val oldState = _viewState.value) {
                is ViewState.Default -> {
                    if(oldState.productsViewState !is ProductsViewState.Presentation) return@launch

                    val newProductsState = changeCategoryMark(oldState.productsViewState, categoryId) ?: return@launch

                    _viewState.value = oldState.copy(newProductsState)

                    reloadTimer {
                        reloadWithFilter(newProductsState, searchField.value)
                    }
                }
                is ViewState.Filter -> {
                    if(oldState.productsViewState !is ProductsViewState.Presentation) return@launch

                    val newProductsState = changeCategoryMark(oldState.productsViewState, categoryId) ?: return@launch

                    _viewState.value = oldState.copy(newProductsState)

                    reloadTimer {
                        reloadWithFilter(newProductsState, searchField.value)
                    }
                }
            }
        }
    }

    private fun viewStateLoading() = ViewState.Default(
        productsViewState = ProductsViewState.Loading,
        newProductsViewState = NewProductsViewState.Loading
    )

    private fun <T> RequestResult<T>.checkForError(): T? {
        if(this is RequestResult.Error) {
            updateState { oldState ->
                when(oldState) {
                    is ViewState.Default -> {
                        oldState.copy(
                            productsViewState = ProductsViewState.Error(message?:"")
                        )
                    }
                    is ViewState.Filter -> {
                        ViewState.Filter(
                            productsViewState = ProductsViewState.Error(message?:"")
                        )
                    }
                }
            }
            return null
        } else return getSuccessResult().data
    }

    sealed interface ViewState {
        data class Default(
            val productsViewState: ProductsViewState,
            val newProductsViewState: NewProductsViewState,
        ) : ViewState

        data class Filter(
            val productsViewState: ProductsViewState,
        ) : ViewState
    }

    sealed interface NewProductsViewState {
        object Loading : NewProductsViewState
        object Empty : NewProductsViewState
        data class Error(val message: String) : NewProductsViewState
        data class Presentation(val newProducts: List<NewProduct>) : NewProductsViewState
    }

    sealed interface ProductsViewState {
        object  Loading : ProductsViewState
        data class Presentation(
            val products: GroupedProducts,
            val categories: List<ViewCategory>
        ) : ProductsViewState
        object Empty : ProductsViewState
        data class Error(val message: String) : ProductsViewState
    }
}