package com.prac.market.model

data class Result(
    val success : Boolean,
    val existAccount : Boolean,
    val error : String,
    val login_token:String?
)