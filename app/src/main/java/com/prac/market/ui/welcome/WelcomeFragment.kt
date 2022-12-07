package com.prac.market.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.prac.market.R
import com.prac.market.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_welcome.*

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel : WelcomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        welcome_start_btn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment) }
    }
}