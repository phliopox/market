package com.prac.market.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.prac.market.databinding.FragmentWelcomeBinding

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
        binding.lifecycleOwner = viewLifecycleOwner

        super.onViewCreated(view, savedInstanceState)
        val welcomeAdapter = WelcomeAdapter()
        binding.vpWelcome.adapter = welcomeAdapter
        viewModel.welcomeBanner.observe(viewLifecycleOwner){
                welcomeBanner -> welcomeAdapter.submitList(welcomeBanner)
        }


    }
}