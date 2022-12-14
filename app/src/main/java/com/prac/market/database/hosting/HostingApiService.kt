package com.prac.market.database.hosting

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface HostingApiService {
    @FormUrlEncoded
    @POST("market_signin.php")
    suspend fun addAccount(@Field("Email") email : String,
                              @Field("Password") password : String) : Result

    @FormUrlEncoded
    @POST("market_login.php")
    suspend fun login(@Field("Email") email : String,
                        @Field("Password")password: String):Result
}
data
class Result(
    val success : Boolean,
    val existAccount : Boolean,
    val error : String,
    val login_token:String?
)