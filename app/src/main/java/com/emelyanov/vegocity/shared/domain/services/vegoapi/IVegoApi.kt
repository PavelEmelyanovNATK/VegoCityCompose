package com.emelyanov.vegocity.shared.domain.services.vegoapi

import androidx.annotation.Keep
import com.emelyanov.vegocity.shared.domain.models.request.GetProductsRequest
import com.emelyanov.vegocity.shared.domain.models.response.CategoryResponse
import com.emelyanov.vegocity.shared.domain.models.response.ProductDetailsResponse
import com.emelyanov.vegocity.shared.domain.models.response.ProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface IVegoApi {
    @POST("products/get-with-filter")
    suspend fun fetchProducts(
        @Body getProductsRequest: GetProductsRequest
    ): List<ProductResponse>

    @GET("products/get/{id}")
    suspend fun fetchProductDetails(
        @Path("id") id: String
    ): ProductDetailsResponse

    @GET("products/get-short/{id}")
    suspend fun fetchProduct(
        @Path("id") id: String
    ): ProductResponse

    @GET("categories/get-all")
    suspend fun fetchCategories(): List<CategoryResponse>
}