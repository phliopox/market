package com.prac.market.database.firebase

import com.prac.market.core.API_BASE_URL
import com.prac.market.BuildConfig
import com.prac.market.database.HomeRepository
import com.prac.market.database.WelcomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * DataBase,Dao,Repository -> Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
class FireBaseModule {

    @Provides
    fun provideBaseUrl() = API_BASE_URL

    @Singleton
    @Provides
    @Named("okhttp1")
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
    @Named("provideRetrofit")
    fun provideRetrofit(@Named("okhttp1")okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(@Named("provideRetrofit") retrofit: Retrofit): FireBaseApiService {
            return retrofit.create(FireBaseApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(fireBaseApiService: FireBaseApiService) = HomeRepository(fireBaseApiService)


    @Singleton
    @Provides
    fun provideWelcomeRepository(fireBaseApiService: FireBaseApiService) = WelcomeRepository(fireBaseApiService)

}