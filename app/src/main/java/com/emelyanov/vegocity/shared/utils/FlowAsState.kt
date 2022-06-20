package com.emelyanov.vegocity.shared.utils

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//@Composable
//fun <T> StateFlow<T>.observeAsState(): State<T> {
//    val state = remember { mutableStateOf(value) }
//    val lifecycleOwner = LocalLifecycleOwner.current
//    LaunchedEffect(key1 = true) {
//        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            this@observeAsState.onEach {
//                state.value = it
//            }.launchIn(this)
//        }
//    }
//    return state
//}