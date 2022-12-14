package com.prac.market

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.prac.market.core.DEFAULT_STRING
import com.prac.market.core.IS_FIRST_RUN
import com.prac.market.core.KEY_USER_ID
import com.prac.market.core.LOGIN
import com.prac.market.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen() // splash 화면

        super.onCreate(savedInstanceState)

        //getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
        val firstRunPref = this.getSharedPreferences(IS_FIRST_RUN, Activity.MODE_PRIVATE)
        val firstRun = firstRunPref.getBoolean(IS_FIRST_RUN, true)

        val autologinPref= getSharedPreferences(LOGIN,Activity.MODE_PRIVATE)
        val string = autologinPref.getString(KEY_USER_ID, DEFAULT_STRING)



        Log.d("MainActivity 1-1", string.toString())

        if (firstRun&&string.isNullOrEmpty()) {

            Log.d("if true", "앱 최초실행 true")
            // 앱 최초 실행시 welcome viewpage 노출

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)

            firstRunPref.edit().putBoolean("isFirst", false).apply()

        } else {

            setContentView(R.layout.activity_main)

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation_main)
            bottomNavigationView.itemIconTintList = null

            val navController =
                supportFragmentManager.findFragmentById(R.id.container_main)?.findNavController()
            navController?.let {
                bottomNavigationView.setupWithNavController(it)
            }
       /*     if(!firstRun&&string.isNullOrEmpty()){
                findNavController().navigate()
            }*/
        }
    }

}