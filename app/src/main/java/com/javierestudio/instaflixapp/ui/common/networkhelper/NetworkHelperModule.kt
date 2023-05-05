package com.javierestudio.instaflixapp.ui.common.networkhelper

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkHelperModule {

    @Provides
    @Reusable
    fun provideNetworkHelper(networkHelperImpl: NetworkHelperImpl): NetworkHelper = networkHelperImpl
}
