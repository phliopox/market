package com.prac.market.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prac.market.model.HomeSections

@Database(entities = [HomeSections::class], version = 1, exportSchema = false)
abstract class HomeDatabase : RoomDatabase() {
    abstract  fun homeDao():HomeDAO

    companion object{
        const val DATABASE_NAME="market.db"
    }
}