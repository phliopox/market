package com.prac.market.database

import com.prac.market.model.Banners
import com.prac.market.model.WelcomeBanner
import retrofit2.http.GET

interface ApiService {
    @GET("home_sections.json") // 홈 섹션 배너 전체 get
    suspend fun getHomeSections(): List<Banners>

    @GET("welcome_page.json")
    suspend fun getWelcomePage() : List<WelcomeBanner>
}