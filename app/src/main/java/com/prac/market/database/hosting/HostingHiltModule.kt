package com.prac.market.database.hosting

import com.prac.market.core.HOSTING_WEB_URL
import com.prac.market.database.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class HostingHiltModule {
    @Provides
    fun provideHostingWebUrl() = HOSTING_WEB_URL

    @Singleton
    @Provides
    @Named("provideHostingRetrofit")
    fun provideHostingRetrofit(@Named("okhttp1") okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideHostingWebUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideWebApiService(@Named("provideHostingRetrofit") retrofit: Retrofit): HostingApiService {
        return retrofit.create(HostingApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAccountRepository(hostingApiService: HostingApiService) = AccountRepository(hostingApiService)


}