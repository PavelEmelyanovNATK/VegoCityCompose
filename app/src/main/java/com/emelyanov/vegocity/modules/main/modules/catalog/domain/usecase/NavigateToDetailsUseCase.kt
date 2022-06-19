package com.emelyanov.vegocity.modules.main.modules.catalog.domain.usecase

import com.emelyanov.vegocity.navigation.core.CoreDestinations
import com.emelyanov.vegocity.navigation.core.CoreNavProvider
import javax.inject.Inject


class NavigateToDetailsUseCase
@Inject
constructor(
    private val coreNavProvider: CoreNavProvider
) {
    operator fun invoke(id: String) {
        coreNavProvider.requestNavigate(CoreDestinations.ProductDetails(id))
    }
}