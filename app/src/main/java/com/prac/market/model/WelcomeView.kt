package com.prac.market.model

import com.google.gson.annotations.SerializedName

data class WelcomeBanner(
    val index : Int,
    @SerializedName("image_url") val imageUrl : String,
    val text : String
)