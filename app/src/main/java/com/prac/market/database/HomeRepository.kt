package com.prac.market.database

import com.prac.market.model.HomeSections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(private val homeDAO: HomeDAO) {
    suspend fun getHomeSections():HomeSections{
        return withContext(Dispatchers.IO){
            homeDAO.getHomeSections()
        }
    }

    suspend fun saveHomeSection(homeSections: HomeSections){
        withContext(Dispatchers.IO){
            homeDAO.insertHomeSections(homeSections)
        }
    }
}