package com.emelyanov.vegocity.modules.orderregistration.domain.usecases

import com.emelyanov.vegocity.navigation.core.CoreDestinations
import com.emelyanov.vegocity.navigation.core.CoreNavProvider
import javax.inject.Inject


class PopBackUseCase
@Inject
constructor(
    private val coreNavProvider: CoreNavProvider
) {
    operator fun invoke() {
        coreNavProvider.requestNavigate(CoreDestinations.PopBack)
    }
}