package com.prac.market.ui.common

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("RegDate")
fun regDateBindingAdapter(date: String,view :TextView) {
    val cal = Calendar.getInstance()
    val df : DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    //date string 을 date 타입으로 변경
    val regDate = df.parse(date)
    var calDate = (cal.time.time-regDate.time)/(60*60*24*1000)
    Log.d("test 날짜", calDate.toString());

    view.text=calDate.toString()
}