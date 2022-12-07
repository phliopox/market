package com.prac.market.ui.home.itemadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.market.databinding.ItemSeasonalBundleBinding
import com.prac.market.model.Banner

class SeasonalProductAdapter :ListAdapter<Banner,SeasonalProductAdapter.SeasonalProductViewHolder>(BannerDiffCallBack()){
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SeasonalProductViewHolder {
    val binding = ItemSeasonalBundleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return SeasonalProductViewHolder(binding)
}

override fun onBindViewHolder(holder: SeasonalProductViewHolder, position: Int) {
    holder.bind(getItem(position))
}
    inner class SeasonalProductViewHolder (private val binding : ItemSeasonalBundleBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(banner:Banner){
            binding.banner =banner
            binding.executePendingBindings()
        }
    }
}