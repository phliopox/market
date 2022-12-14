package com.prac.market.ui.welcome

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.prac.market.R
import com.prac.market.core.DEFAULT_STRING
import com.prac.market.core.IS_FIRST_RUN
import com.prac.market.core.KEY_USER_ID
import com.prac.market.core.LOGIN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val findNavController =
            supportFragmentManager.findFragmentById(R.id.container_welcome)?.findNavController()

        val firstRunPref = this.getSharedPreferences(IS_FIRST_RUN, Activity.MODE_PRIVATE)
        val firstRun = firstRunPref.getBoolean(IS_FIRST_RUN, true)

        /*val autologinPref= getSharedPreferences(LOGIN, Activity.MODE_PRIVATE)
        val string = autologinPref.getString(KEY_USER_ID, DEFAULT_STRING)*/

        if(!firstRun){
            findNavController?.navigateUp()
            findNavController?.navigate(R.id.loginFragment)
        }
    }
}
