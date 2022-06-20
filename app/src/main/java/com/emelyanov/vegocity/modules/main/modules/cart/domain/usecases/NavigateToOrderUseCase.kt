package com.emelyanov.vegocity.modules.main.modules.cart.domain.usecases

import com.emelyanov.vegocity.navigation.core.CoreDestinations
import com.emelyanov.vegocity.navigation.core.CoreNavProvider
import javax.inject.Inject


class NavigateToOrderUseCase
@Inject
constructor(
    private val coreNavProvider: CoreNavProvider
) {
    operator fun invoke() {
        coreNavProvider.requestNavigate(CoreDestinations.OrderRegistration)
    }
}