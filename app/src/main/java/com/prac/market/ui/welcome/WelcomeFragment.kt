package com.prac.market.ui.welcome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.prac.market.MainActivity
import com.prac.market.core.IS_FIRST_RUN
import com.prac.market.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel : WelcomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false)
        return binding.root    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWelcomeBanner()
        clickStartBtn()
    }

    private fun setWelcomeBanner() {
        with(binding.vpWelcome) {
            adapter = WelcomeAdapter().apply {
                viewModel.welcomeBanner.observe(viewLifecycleOwner) { welcomeBanner ->
                    submitList(welcomeBanner)
                }
            }
            TabLayoutMediator(
                binding.welcomeIvIndicator,
                this
            )
            { tab, position -> }.attach()
        }
    }

    private fun clickStartBtn() {
        binding.welcomeStartBtn.setOnClickListener {
            val firstRunPref =
                requireActivity().getSharedPreferences(IS_FIRST_RUN, Activity.MODE_PRIVATE)
            firstRunPref.edit().putBoolean("isFirst", false).apply()

            val intent = Intent(requireActivity() , MainActivity::class.java)
            startActivity(intent)

        }
    }
}