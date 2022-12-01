package com.prac.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.prac.market.databinding.FragmentHomeBinding
import com.prac.market.ui.HomeBannerAdapter
import com.prac.market.ui.HomeSectionTitleAdapter
import com.prac.market.ui.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        val homeSectionTitleAdapter = HomeSectionTitleAdapter()
        val homeBannerAdapter = HomeBannerAdapter()
        binding.rvHomeSection.adapter = ConcatAdapter(homeSectionTitleAdapter,homeBannerAdapter)
        viewModel.homeData.observe(viewLifecycleOwner){
            homeData->homeSectionTitleAdapter.submitList(homeData)
        }
    }
}