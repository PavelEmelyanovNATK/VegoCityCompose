package com.emelyanov.vegocity.shared.domain.usecases

import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.ViewProduct
import com.emelyanov.vegocity.shared.domain.models.view.ViewCategory
import javax.inject.Inject


class GroupProductsUseCase
@Inject
constructor(

) {
    operator fun invoke(
        categories: List<ViewCategory>,
        products: List<ViewProduct>
    ) : Map<String, List<ViewProduct>> {
        val map = mutableMapOf<String, MutableList<ViewProduct>>()

        products.forEach { product ->
            val category = categories.find { it.id == product.categoryId }?.name
            if(map[requireNotNull(category)] == null) map[category] = mutableListOf(product)
            else map[category]?.add(product)
        }

        return map
    }
}