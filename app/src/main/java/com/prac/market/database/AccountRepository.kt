package com.prac.market.database


import com.prac.market.database.hosting.HostingApiService
import com.prac.market.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepository @Inject constructor(private val hostingApiService : HostingApiService) {
    suspend fun addNewAccount(email : String, password:String):Result{
        return withContext(Dispatchers.IO){
            hostingApiService.addAccount(email,password)
        }
    }

    suspend fun login(email: String, password: String): Result {
        return withContext(Dispatchers.IO) {
            hostingApiService.login(email, password)
        }
    }
}