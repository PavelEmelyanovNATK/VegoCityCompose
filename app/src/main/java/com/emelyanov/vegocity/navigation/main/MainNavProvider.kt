package com.emelyanov.vegocity.navigation.main

import androidx.lifecycle.ViewModel
import com.emelyanov.vegocity.shared.utils.BaseNavProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainNavProvider : BaseNavProvider<MainDestinations>()

@HiltViewModel
class MainNavHolder
@Inject
constructor(
    val mainNavProvider: MainNavProvider
) : ViewModel()