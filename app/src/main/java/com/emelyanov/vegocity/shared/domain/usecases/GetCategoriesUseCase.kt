package com.emelyanov.vegocity.shared.domain.usecases

import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.view.ViewCategory
import com.emelyanov.vegocity.shared.domain.models.view.toViewCategory
import com.emelyanov.vegocity.shared.domain.services.productsrepo.IProductsRepository
import javax.inject.Inject

class GetCategoriesUseCase
@Inject
constructor(
    private val productsRepository: IProductsRepository
) {
    suspend operator fun invoke() : RequestResult<List<ViewCategory>> {
        return productsRepository.getCategories().map {
            it.map {
                it.toViewCategory()
            }
        }
    }
}