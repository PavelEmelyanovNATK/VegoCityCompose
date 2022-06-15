package com.emelyanov.vegocity.modules.main.modules.catalog.domain

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Category
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.NewProduct
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Product
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase.GetCategoriesUseCase
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase.GetNewProductsUseCase
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase.GetProductsUseCase
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase.GroupProductsUseCase
import com.emelyanov.vegocity.shared.domain.di.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias GroupedProducts = Map<String, List<Product>>

@HiltViewModel
class CatalogViewModel
@Inject
constructor(
    private val getCategories: GetCategoriesUseCase,
    private val getNewProducts: GetNewProductsUseCase,
    private val getProducts: GetProductsUseCase,
    private val groupProducts: GroupProductsUseCase,
) : BaseStateViewModel<CatalogViewModel.ViewState>(
    ViewState.Default(
        productsViewState = ProductsViewState.Loading,
        newProductsViewState = NewProductsViewState.Loading
    )
) {
    private val _searchField = MutableStateFlow("")
    val searchField: StateFlow<String> = _searchField

    private var currentTimerValue = 2000
    private var timer: Job? = null
    private fun reloadTimer(onComplete: suspend () -> Unit) {
        if(timer != null && timer?.isActive == true) timer?.cancel()
        currentTimerValue = 2000
        timer = viewModelScope.launch {
            while(currentTimerValue > 0) {
                currentTimerValue -= 100
                delay(100)
            }
            onComplete()
        }
    }

    init {
        reloadToDefault()
    }

    private fun reloadToDefault() {
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
            try {
                val categories = getCategories()
                val products = getProducts()
                val groupedProducts = groupProducts(categories, products)

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
            } catch (ex: Exception) {
                updateState { oldState ->
                    if(oldState is ViewState.Default)
                        oldState.copy(
                            productsViewState = ProductsViewState.Error("Ошибка")
                        )
                    else
                        ViewState.Default(
                            productsViewState = ProductsViewState.Error("Ошибка"),
                            newProductsViewState = NewProductsViewState.Loading
                        )
                }
            }
        }
    }

    private fun getGroupedProducts(): GroupedProducts {
        val categories = getCategories()
        val products = getProducts()
        return groupProducts(categories, products)
    }

    private fun reloadWithFilter(
        productsViewState: ProductsViewState.Presentation,
        searchField: String
    ) {
        if(productsViewState.categories.any { it.isSelected } || searchField.isNotEmpty()) {
            updateState {
                ViewState.Filter(
                    productsViewState = productsViewState.copy(
                        products = getGroupedProducts()
                    )
                )
            }
        } else {
            reloadToDefault()
        }
    }

    fun searchFiledChanged(text: String) {
        _searchField.value = text

        reloadTimer {
            when(val oldState = _viewState.value)  {
                is ViewState.Filter -> {
                    if(oldState.productsViewState !is ProductsViewState.Presentation) return@reloadTimer
                    reloadWithFilter(oldState.productsViewState, _searchField.value)
                }
                is ViewState.Default -> {
                    if(oldState.productsViewState !is ProductsViewState.Presentation) return@reloadTimer
                    reloadWithFilter(oldState.productsViewState, _searchField.value)
                }
            }
        }
    }

    private fun changeCategoryMark(
        productsViewState: ProductsViewState.Presentation,
        categoryId: String
    ) : ProductsViewState.Presentation? {
        val curCategory = productsViewState.categories.find { it.id == categoryId } ?: return null

        productsViewState.categories.toMutableList()
        val newCategories = productsViewState.categories.toMutableList().apply {
            set(indexOf(curCategory), curCategory.copy(isSelected = !curCategory.isSelected))
        }

        return productsViewState.copy(
            categories = newCategories
        )
    }

    fun categoryClicked(categoryId: String) {
        when (val oldState = _viewState.value) {
            is ViewState.Default -> {
                updateState {
                    ViewState.Filter(
                        productsViewState = ProductsViewState.Loading
                    )
                }

                if(oldState.productsViewState !is ProductsViewState.Presentation) return

                val newProductsState = changeCategoryMark(oldState.productsViewState, categoryId) ?: return

                reloadWithFilter(newProductsState, searchField.value)
            }
            is ViewState.Filter -> {
                updateState {
                    ViewState.Filter(
                        productsViewState = ProductsViewState.Loading
                    )
                }

                if(oldState.productsViewState !is ProductsViewState.Presentation) return

                val newProductsState = changeCategoryMark(oldState.productsViewState, categoryId) ?: return

                reloadWithFilter(newProductsState, searchField.value)
            }
        }
    }

    private fun viewStateLoading() = ViewState.Default(
        productsViewState = ProductsViewState.Loading,
        newProductsViewState = NewProductsViewState.Loading
    )

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
            val categories: List<Category>
        ) : ProductsViewState
        object Empty : ProductsViewState
        data class Error(val message: String) : ProductsViewState
    }
}