package com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase

import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Product
import javax.inject.Inject


class GetProductsUseCase
@Inject
constructor(

) {
    operator fun invoke() : List<Product> {
        return listOf(
            Product(
                id = "p1",
                photoRef = "",
                title = "Product 1",
                isNew = false,
                price = 1999,
                actualPrice = 1999,
                categoryId = "c1"
            ),
            Product(
                id = "p2",
                photoRef = "",
                title = "Product 2",
                isNew = false,
                price = 1999,
                actualPrice = 1999,
                categoryId = "c1"
            ),
            Product(
                id = "p3",
                photoRef = "",
                title = "Product 3",
                isNew = false,
                price = 1999,
                actualPrice = 1999,
                categoryId = "c1"
            ),
            Product(
                id = "p4",
                photoRef = "",
                title = "Product 4",
                isNew = false,
                price = 1999,
                actualPrice = 1999,
                categoryId = "c2"
            ),
            Product(
                id = "p5",
                photoRef = "",
                title = "Product 5",
                isNew = false,
                price = 1999,
                actualPrice = 1999,
                categoryId = "c2"
            ),
            Product(
                id = "p6",
                photoRef = "",
                title = "Product 6",
                isNew = false,
                price = 1999,
                actualPrice = 1999,
                categoryId = "c2"
            ),
            Product(
                id = "p7",
                photoRef = "",
                title = "Product 7",
                isNew = false,
                price = 1999,
                actualPrice = 1999,
                categoryId = "c3"
            ),
        )
    }
}