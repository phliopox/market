package com.prac.market.database

import com.prac.market.core.API_BASE_URL
import com.prac.market.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * DataBase,Dao,Repository -> Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
class DiModule {

    @Provides
    fun provideBaseUrl() = API_BASE_URL

    @Singleton
    @Provides
    fun provideOKHttpClient() = if (BuildConfig.DEBUG){
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC }

        OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }else{
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit):ApiService{
            return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(apiService: ApiService) = HomeRepository(apiService)


    @Singleton
    @Provides
    fun provideWelcomeRepository(apiService: ApiService)=WelcomeRepository(apiService)
}