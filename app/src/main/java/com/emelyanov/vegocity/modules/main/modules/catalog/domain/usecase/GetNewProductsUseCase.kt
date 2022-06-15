package com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase

import com.emelyanov.vegocity.modules.main.modules.catalog.domain.models.NewProduct
import javax.inject.Inject


class GetNewProductsUseCase
@Inject
constructor(

) {
    operator fun invoke() : List<NewProduct> {
        return listOf(
            NewProduct(""),
            NewProduct(""),
            NewProduct("")
        )
    }
}