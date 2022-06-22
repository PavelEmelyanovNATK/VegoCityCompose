package com.emelyanov.vegocity.shared.domain.utils

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