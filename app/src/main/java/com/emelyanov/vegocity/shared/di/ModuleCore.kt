package com.emelyanov.vegocity.shared.di

import android.content.Context
import com.emelyanov.vegocity.shared.domain.services.cartrepo.CartRepository
import com.emelyanov.vegocity.shared.domain.services.cartrepo.ICartRepository
import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.FavoritesRepository
import com.emelyanov.vegocity.shared.domain.services.favoritesrepo.IFavoritesRepository
import com.emelyanov.vegocity.shared.domain.services.localstorage.CartStorage
import com.emelyanov.vegocity.shared.domain.services.localstorage.ILocalStorage
import com.emelyanov.vegocity.shared.domain.services.productsrepo.IProductsRepository
import com.emelyanov.vegocity.shared.domain.services.productsrepo.ProductsRepository
import com.emelyanov.vegocity.shared.domain.services.vegoapi.IVegoApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
class ModuleCore {
    private val json = Json { ignoreUnknownKeys = true }
    private val mediaType = ("application/json; charset=utf-8").toMediaType()

    @Singleton
    @Provides
    fun provideVegoApi(): IVegoApi = Retrofit.Builder()
        .baseUrl("http://10.147.17.238:3000")
        .addConverterFactory(json.asConverterFactory(mediaType))
        .build()
        .create(IVegoApi::class.java)

    @Singleton
    @Provides
    fun provideProductsRepository(
        vegoApi: IVegoApi
    ): IProductsRepository
    = ProductsRepository(vegoApi)

    @Singleton
    @Provides
    fun provideCartStorage(@ApplicationContext context: Context): ILocalStorage = CartStorage(context)

    @Singleton
    @Provides
    fun provideCartRepository(localStorage: ILocalStorage): ICartRepository = CartRepository(localStorage)

    @Singleton
    @Provides
    fun provideFavoritesRepository(vegoApi: IVegoApi, localStorage: ILocalStorage): IFavoritesRepository = FavoritesRepository(vegoApi, localStorage)
}
