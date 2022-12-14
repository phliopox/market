package com.prac.market

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.common.KakaoSdk
import com.prac.market.core.*
import com.prac.market.ui.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen() // splash 화면

        super.onCreate(savedInstanceState)

        val firstRunPref = this.getSharedPreferences(IS_FIRST_RUN, Activity.MODE_PRIVATE)
        val firstRun = firstRunPref.getBoolean(IS_FIRST_RUN, true)
        val autologinPref= getSharedPreferences(LOGIN,Activity.MODE_PRIVATE)
        val string = autologinPref.getString(KEY_USER_ID, DEFAULT_STRING)

        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)

        if (firstRun&&string.isNullOrEmpty()) {
            // 앱 최초 실행시 welcome viewpage 노출
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
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