package com.prac.market.ui.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.prac.market.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
}
