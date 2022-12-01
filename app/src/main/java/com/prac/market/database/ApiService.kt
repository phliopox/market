package com.prac.market.database

import com.prac.market.model.Banners
import com.prac.market.model.HomeSections
import retrofit2.http.GET

interface ApiService {
    @GET("home_sections.json") // 홈 섹션 배너 전체 get
    fun getHomeSections(): List<Banners>
}