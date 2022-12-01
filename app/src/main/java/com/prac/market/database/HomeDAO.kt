package com.prac.market.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prac.market.model.HomeSections

@Dao
interface HomeDAO {
    @Query("SELECT * FROM HomeSections")
    fun getHomeSections(): HomeSections

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHomeSections(homeSections: HomeSections) : Long
}