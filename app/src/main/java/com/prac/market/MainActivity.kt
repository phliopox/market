package com.prac.market

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen() // splash 화면

        val pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
        val first = pref.getBoolean("isFirst", false)
        if(!first){
            Log.d("MainActivity","앱 최초실행 true")
            val edit = pref.edit()
            edit.putBoolean("isFirst",true)
            edit.commit()
            // 앱 최초 실행시 하고 싶은 작업
        }else{
            Log.d("Is First Time?" , "not first.")
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
        bottomNavigationView.itemIconTintList = null

        val navController =
            supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
        navController?.let {
            bottomNavigationView.setupWithNavController(it)
        }
    }


}