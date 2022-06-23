package com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase

import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.ViewProduct
import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.toViewProduct
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.view.ViewCategory
import com.emelyanov.vegocity.shared.domain.services.productsrepo.IProductsRepository
import javax.inject.Inject


private val products = listOf(
    ViewProduct(
        id = "p1",
        photoUrl = "",
        title = "Product 1",
        isNew = false,
        price = 1999,
        actualPrice = 1999,
        category = "c1"
    ),
    ViewProduct(
        id = "p2",
        photoUrl = "",
        title = "Product 2",
        isNew = false,
        price = 1999,
        actualPrice = 1999,
        category = "c1"
    ),
    ViewProduct(
        id = "p3",
        photoUrl = "",
        title = "Product 3",
        isNew = false,
        price = 1999,
        actualPrice = 1999,
        category = "c1"
    ),
    ViewProduct(
        id = "p4",
        photoUrl = "",
        title = "Product 4",
        isNew = false,
        price = 1999,
        actualPrice = 1999,
        category = "c2"
    ),
    ViewProduct(
        id = "p5",
        photoUrl = "",
        title = "Product 5",
        isNew = false,
        price = 1999,
        actualPrice = 1999,
        category = "c2"
    ),
    ViewProduct(
        id = "p6",
        photoUrl = "",
        title = "Product 6",
        isNew = false,
        price = 1999,
        actualPrice = 1999,
        category = "c2"
    ),
    ViewProduct(
        id = "p7",
        photoUrl = "",
        title = "Product 7",
        isNew = false,
        price = 1999,
        actualPrice = 1999,
        category = "c3"
    ),
)

class GetProductsUseCase
@Inject
constructor(
    private val productsRepository: IProductsRepository
) {
    suspend operator fun invoke(categories: List<ViewCategory> = listOf(), searchFilter: String = "") : RequestResult<List<ViewProduct>> {
        return productsRepository.getProducts(
            categories = categories.map { it.id.toInt() },
            filter = searchFilter
        ).map {
            it.map {
                it.toViewProduct()
            }
        }
    }
}