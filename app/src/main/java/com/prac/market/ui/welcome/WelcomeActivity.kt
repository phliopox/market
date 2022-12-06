package com.prac.market.ui.welcome

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.prac.market.R


class WelcomeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        findNavController(1)
        val findViewById = findViewById<Navigation>(R.id.navigation_welcome)


    }
}