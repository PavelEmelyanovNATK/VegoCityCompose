package com.emelyanov.vegocity.navigation.main

sealed class MainDestinations(val route: String) {
    object Catalog: MainDestinations("main/catalog")
    object Favorites: MainDestinations("main/favorites")
    object AboutUs: MainDestinations("main/about_us")
    object PopBack: MainDestinations("")
}