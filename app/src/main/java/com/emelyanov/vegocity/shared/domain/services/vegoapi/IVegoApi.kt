package com.emelyanov.vegocity.shared.domain.services.vegoapi

interface IVegoApi {
    fun fetchProducts(
        categories: List<Int> = listOf(),
        filter: String = "",
        pagesCount: Int = 1
    )
}