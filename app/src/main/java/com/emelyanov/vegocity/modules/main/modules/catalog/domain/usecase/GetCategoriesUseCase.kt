package com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase

import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.Category
import javax.inject.Inject


class GetCategoriesUseCase
@Inject
constructor(

) {
    operator fun invoke() : List<Category> {
        return listOf(
            Category(
                id = "c1",
                name = "Category 1"
            ),
            Category(
                id = "c2",
                name = "Category 2"
            ),
            Category(
                id = "c3",
                name = "Category 3"
            ),
        )
    }
}