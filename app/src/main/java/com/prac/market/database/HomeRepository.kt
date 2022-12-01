package com.prac.market.database

import com.prac.market.model.Banners
import com.prac.market.model.HomeSections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getHomeSections():List<Banners>{
        return withContext(Dispatchers.IO){
            apiService.getHomeSections()
        }
    }
}