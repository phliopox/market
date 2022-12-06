package com.prac.market.ui.welcome

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.prac.market.R
import com.prac.market.databinding.ActivityWelcomeBinding


class WelcomeActivity : Fragment() {
    private lateinit var binding: ActivityWelcomeBinding
    private val viewModel : WelcomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("WelcomeActivity","호출됨.")

       // setContentView(R.layout.activity_welcome)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_welcome)
        binding.vpWelcome.adapter = WelcomeAdapter()



    }
}