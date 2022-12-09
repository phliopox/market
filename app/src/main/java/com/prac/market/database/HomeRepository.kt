package com.prac.market.database

import com.prac.market.database.firebase.FireBaseApiService
import com.prac.market.model.Banners
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(private val fireBaseApiService: FireBaseApiService) {
    suspend fun getHomeSections():List<Banners>{
        return withContext(Dispatchers.IO){
            fireBaseApiService.getHomeSections()
        }
    }
}