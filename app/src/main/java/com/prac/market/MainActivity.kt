package com.prac.market

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prac.market.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen() // splash 화면

        super.onCreate(savedInstanceState)


        val pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
        val firstRun = pref.getBoolean("isFirst", true)
        if (firstRun) {

            Log.d("if true", "앱 최초실행 true")
            // 앱 최초 실행시 하고 싶은 작업 welcome viewpage 노출

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)

           // pref.edit().putBoolean("isFirst", false).apply()

        } else {

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

}