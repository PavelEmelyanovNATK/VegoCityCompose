package com.emelyanov.vegocity.shared.domain.models

sealed interface FavoriteItem {
    data class Normal(
        val id: String,
        val title: String,
        val photoUrl: String,
        val category: String,
        val price: Double,
    ) : FavoriteItem

    data class Outdated(
        val id: String,
        val title: String
    ) : FavoriteItem
}