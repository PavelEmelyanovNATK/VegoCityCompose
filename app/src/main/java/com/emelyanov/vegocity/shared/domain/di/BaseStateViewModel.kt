package com.emelyanov.vegocity.shared.domain.di

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseStateViewModel<S>(
    initialState: S
) : ViewModel() {
    private val TAG = this::class.java.simpleName

    protected val _viewState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState

    fun updateState(block: (oldState: S) -> S?) {
        val newState = block(_viewState.value)
        Log.d(TAG, "" + (newState.hashCode() == _viewState.value.hashCode()))
        _viewState.value = newState ?: return
    }
}