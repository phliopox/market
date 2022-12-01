package com.prac.market.database

import com.prac.market.model.HomeSections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(private val apiService: ApiService) {
    suspend fun getHomeSections():HomeSections{
        return withContext(Dispatchers.IO){
            apiService.getHomeSections()
        }
    }
}