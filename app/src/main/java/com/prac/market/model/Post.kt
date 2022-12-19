package com.prac.market.model

import java.util.*

data class Post (
    val id : Int,
    val title : String,
    val writer : String,
    val content : String,
    val date : Date
)