package com.emelyanov.vegocity.navigation.core

import androidx.lifecycle.ViewModel
import com.emelyanov.vegocity.shared.utils.BaseNavProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class CoreNavProvider : BaseNavProvider<CoreDestinations>()

@HiltViewModel
class CoreNavHolder
@Inject
constructor(
    val coreNavProvider: CoreNavProvider
) : ViewModel()