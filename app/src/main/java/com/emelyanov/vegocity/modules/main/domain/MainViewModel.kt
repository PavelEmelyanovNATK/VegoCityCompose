package com.emelyanov.vegocity.modules.main.domain

import androidx.lifecycle.ViewModel
import com.emelyanov.vegocity.shared.domain.services.cartrepo.CartRepository
import com.emelyanov.vegocity.shared.domain.services.cartrepo.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    cartRepository: ICartRepository
) : ViewModel() {
    val cartItemsCount = cartRepository.cartFlow.map { list ->
        list.sumOf { it.count }
    }
}