package com.emelyanov.vegocity.shared.domain.services.productsrepo

import com.emelyanov.vegocity.shared.domain.models.Category
import com.emelyanov.vegocity.shared.domain.models.Product
import com.emelyanov.vegocity.shared.domain.models.ProductDetails
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.response.CategoryResponse
import com.emelyanov.vegocity.shared.domain.models.response.ProductDetailsResponse
import com.emelyanov.vegocity.shared.domain.models.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IProductsRepository {
    suspend fun getProducts(
        categories: List<Int> = listOf(),
        filter: String = ""
    ): RequestResult<List<Product>>

    suspend fun getProductDetails(id: String): RequestResult<ProductDetails>

    suspend fun getCategories(): RequestResult<List<Category>>
}