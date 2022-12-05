package com.prac.market.ui.welcome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.prac.market.MainActivity
import com.prac.market.R
import com.prac.market.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
        val first = pref.getBoolean("isFirst", false)
        if(!first){
            Log.d("MainActivity","앱 최초실행 true")
            val edit = pref.edit()
            edit.putBoolean("isFirst",true)
            edit.commit()
        // 앱 최초 실행시 하고 싶은 작업 welcome viewpage 노출

            binding = DataBindingUtil.setContentView(this,R.layout.activity_welcome)
            binding.vpWelcome.adapter = WelcomeAdapter()

        }else{
            Log.d("Is First Time?" , "not first.")
            Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}