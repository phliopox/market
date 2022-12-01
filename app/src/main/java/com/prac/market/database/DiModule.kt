package com.prac.market.database

import android.content.Context
import androidx.room.Room
import com.prac.market.model.HomeSections
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DataBase,Dao,Repository -> Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
class DiModule {
    @Singleton
    @Provides
    fun provideHomeDatabase(@ApplicationContext context: Context) : HomeDatabase{
        return Room
            .databaseBuilder(
                context,
                HomeDatabase::class.java,
                HomeDatabase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideHomeDAO(homeDB : HomeDatabase ): HomeDAO{
        return homeDB.homeDao()
    }

    @Singleton
    @Provides
    fun provideHomeRepository(homeDAO: HomeDAO):HomeRepository{
        return HomeRepository(homeDAO)
    }
}