package com.phissy.restaurantplug.di.modules

import android.content.Context
import android.content.res.Resources
import com.phissy.restaurantplug.BuildConfig
import com.phissy.restaurantplug.app.App
import com.phissy.restaurantplug.repository.api.ApiServices
import com.phissy.restaurantplug.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [PrefrencesModule::class, ActivityModule::class, ViewModelModule::class])
class AppModule {

    /**
     * Static variables to hold base url's etc.
     */
    companion object {
        private const val BASE_URL = "https://api.yelp.com/v3/"
    }

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideRestaurantService(okHttpClient: OkHttpClient): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .client(okHttpClient)
            .build()
            .create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8"

        val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val response = chain.proceed(chain.request())
            // Do anything with response here
            response.header("Content-Type", APPLICATION_JSON_CHARSET_UTF_8)
            response.header("Accept", APPLICATION_JSON_CHARSET_UTF_8)
            response.header("Authorization", "Bearer _w8KSuJNqX-tcQJ4da_hKFDtbh6QtlhA-OQ5uVwN9kW1wJmKeMmw_1BxpAEMkFyqyynQKde_2vT9Pc_wDjT8EamfE9u0FYza7ZqXJK4cNI_rHKOA8WwWboBjSlTOXXYx")
            response
        }.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        return httpClient.build()
    }


    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }


    /**
     * Application resource provider, so that we can get the Drawable, Color, String etc at runtime
     */
    @Provides
    @Singleton
    fun providesResources(application: App): Resources = application.resources
}
