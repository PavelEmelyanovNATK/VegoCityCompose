package com.emelyanov.vegocity.shared.domain.services.productsrepo

import android.util.Log
import com.emelyanov.vegocity.shared.domain.models.Category
import com.emelyanov.vegocity.shared.domain.models.Product
import com.emelyanov.vegocity.shared.domain.models.ProductDetails
import com.emelyanov.vegocity.shared.domain.models.RequestResult
import com.emelyanov.vegocity.shared.domain.models.request.GetProductsRequest
import com.emelyanov.vegocity.shared.domain.services.vegoapi.IVegoApi
import com.emelyanov.vegocity.shared.domain.utils.vegoRequestWrapper

class ProductsRepository(
    private val vegoApi: IVegoApi
) : IProductsRepository {
    override suspend fun getProducts(categories: List<Int>, filter: String): RequestResult<List<Product>>
    = vegoRequestWrapper {
        vegoApi.fetchProducts(GetProductsRequest(categories, filter)).map {
            Product(
                id = it.id,
                title = it.title,
                category = it.category,
                price = it.price,
                photoUrl = it.photoPath ?: ""
            )
        }
    }

    override suspend fun getProduct(id: String): RequestResult<Product>
    = vegoRequestWrapper {
        vegoApi.fetchProduct(id).let {
            Product(
                id = it.id,
                title = it.title,
                category = it.category,
                price = it.price,
                photoUrl = it.photoPath ?: ""
            )
        }
    }

    override suspend fun getProductDetails(id: String): RequestResult<ProductDetails>
    = vegoRequestWrapper {
        vegoApi.fetchProductDetails(id).let { detailsResponse ->
            val photos = detailsResponse.photos.toMutableList()
                .apply {
                    if(detailsResponse.mainPhotoId == null) return@apply
                    val mainPhoto = find { it.id == detailsResponse.mainPhotoId } ?: return@apply
                    val firstPhoto = first()

                    if(mainPhoto == firstPhoto) return@apply

                    set(indexOf(mainPhoto), firstPhoto)
                    set(0, mainPhoto)
                }.map { it.highResPath }

            ProductDetails(
                id = detailsResponse.id,
                title = detailsResponse.title,
                description = detailsResponse.description,
                price = detailsResponse.price,
                photoUrls = photos
            )
        }
    }

    override suspend fun getCategories(): RequestResult<List<Category>>
    = vegoRequestWrapper {
        vegoApi.fetchCategories().map {
            Category(
                id = it.id,
                name = it.name
            )
        }
    }
}