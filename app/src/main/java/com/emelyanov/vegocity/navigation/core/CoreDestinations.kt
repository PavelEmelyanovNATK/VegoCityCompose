package com.emelyanov.vegocity.navigation.core

import java.util.*

sealed class CoreDestinations(val route: String) {
    object Main : CoreDestinations("main")
    object OrderRegistration : CoreDestinations("order_registration")
    data class ProductDetails(val id: String?) : CoreDestinations("product_details/${id ?: "{id}"}")
    object PopBack : CoreDestinations("")
}
