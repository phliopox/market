package com.prac.market.model

import com.google.gson.annotations.SerializedName

data class HomeSections(
    val home_sections : List<Banners>
    )

data class Banners (
    val title : String,
    val banners : List<Banner>
    )

data class Title(
    val text : String
)

data class Banner (
    val id : String,
    val label : String, // quest의 title, 다른 banner들은 상품명에 해당
    @SerializedName("image_url") val imageUrl : String?,
    @SerializedName("image_url_list") val imageUrlList : List<String>?,
    val description : String?,
    @SerializedName("star_point") val starPoint : Double?
    )