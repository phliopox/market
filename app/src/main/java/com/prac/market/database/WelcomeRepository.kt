package com.prac.market.database

import com.prac.market.database.firebase.FireBaseApiService
import com.prac.market.model.WelcomeBanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WelcomeRepository @Inject constructor(private val fireBaseApiService: FireBaseApiService){
    suspend fun getWelcomePage():List<WelcomeBanner>{
        return withContext(Dispatchers.IO){
            fireBaseApiService.getWelcomePage()
        }
    }
}