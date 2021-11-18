package com.phissy.restaurantplug.di

import com.phissy.restaurantplug.common.Constants
import com.phissy.restaurantplug.data.RestaurantService
import com.phissy.restaurantplug.data.repository.RestaurantRepositoryImpl
import com.phissy.restaurantplug.domain.repository.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRestaurantService(): RestaurantService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestaurantService::class.java)
    }

    @Provides
    @Singleton
    fun providesRestaurantService(api: RestaurantService): RestaurantRepository{
        return RestaurantRepositoryImpl(api)
    }
}