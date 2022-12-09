package com.prac.market.database


import com.prac.market.database.hosting.HostingApiService
import com.prac.market.database.hosting.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class AccountRepository @Inject constructor(private val hostingApiService : HostingApiService) {
    suspend fun addNewAccount(email : String, password:String):Result{
        return withContext(Dispatchers.IO){
            hostingApiService.addAccount(email,password)
        }
    }
}