package com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase

import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Category
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Product
import javax.inject.Inject


class GroupProductsUseCase
@Inject
constructor(

) {
    operator fun invoke(
        categories: List<Category>,
        products: List<Product>
    ) : Map<String, List<Product>> {
        val map = mutableMapOf<String, MutableList<Product>>()

        products.forEach { product ->
            val category = categories.find { it.id == product.categoryId }?.name
            if(map[requireNotNull(category)] == null) map[category] = mutableListOf(product)
            else map[category]?.add(product)
        }

        return map
    }
}