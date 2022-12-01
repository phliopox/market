package com.prac.market.database

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {
    @Singleton
    @Provides
    fun provideNotDatabase(@ApplicationContext context: Context) : NoteDatabase{

    }
}