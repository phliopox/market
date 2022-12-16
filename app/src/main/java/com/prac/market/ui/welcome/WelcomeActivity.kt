package com.prac.market.ui.welcome

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.prac.market.R
import com.prac.market.core.IS_FIRST_RUN
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

        if(!firstRun){
            findNavController?.navigateUp()
            findNavController?.navigate(R.id.snsSignInFragment)
        }
    }
}
