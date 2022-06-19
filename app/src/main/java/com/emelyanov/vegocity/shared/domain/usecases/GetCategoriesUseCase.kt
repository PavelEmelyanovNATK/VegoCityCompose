package com.emelyanov.vegocity.shared.domain.usecases

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
            Category(
                id = "c4",
                name = "Category 4"
            ),
            Category(
                id = "c5",
                name = "Category 5"
            ),
            Category(
                id = "c6",
                name = "Category 6"
            ),
            Category(
                id = "c7",
                name = "Category 7"
            ),
        )
    }
}