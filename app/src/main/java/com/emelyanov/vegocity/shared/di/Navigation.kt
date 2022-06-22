package com.emelyanov.vegocity.shared.di

import com.emelyanov.vegocity.navigation.core.CoreNavProvider
import com.emelyanov.vegocity.navigation.main.MainNavProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Navigation {
    @Singleton
    @Provides
    fun provideCoreNavProvider(): CoreNavProvider = CoreNavProvider()

    @Singleton
    @Provides
    fun provideMainNavProvider(): MainNavProvider = MainNavProvider()
}