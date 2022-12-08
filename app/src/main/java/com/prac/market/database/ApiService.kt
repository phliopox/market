package com.prac.market.database

import android.telecom.Call
import com.prac.market.model.Banners
import com.prac.market.model.WelcomeBanner
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("home_sections.json") // 홈 섹션 배너 전체 get
    suspend fun getHomeSections(): List<Banners>

    @GET("welcome_page.json")
    suspend fun getWelcomePage() : List<WelcomeBanner>

    @FormUrlEncoded
    @POST()
    suspend fun createAccount(@Field("Email") email : String ,
                              @Field("Password") password : String) : Call<Result>()

}
data class Result(
    val success : Boolean,
    val error : String
)