package com.prac.market.ui.home.itemadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.market.databinding.ItemPopularProductBinding
import com.prac.market.model.Banner

class PopularProductAdapter :ListAdapter<Banner, PopularProductAdapter.PopularProductViewHolder>(BannerDiffCallBack()){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularProductViewHolder {
            val binding = ItemPopularProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return PopularProductViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PopularProductViewHolder, position: Int) {
            holder.bind(getItem(position))

        }
    
    inner class PopularProductViewHolder(private val binding: ItemPopularProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(banner:Banner){
            binding.banner =banner
            binding.executePendingBindings()
        }
    }
    
}