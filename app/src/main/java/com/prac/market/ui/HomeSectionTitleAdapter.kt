package com.prac.market.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.market.databinding.HomeSectionTitleBinding
import com.prac.market.model.Banners

class HomeSectionTitleAdapter : ListAdapter<Banners, HomeSectionTitleAdapter.HomeSectionTitleViewHolder>(HomeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSectionTitleViewHolder {
        val binding =
            HomeSectionTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeSectionTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeSectionTitleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class HomeSectionTitleViewHolder(private val binding: HomeSectionTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val nestedAdapter = HomeBannerAdapter()

        init{
            binding.homeBannerView.adapter = nestedAdapter
        }

        fun bind(banner: Banners) {
            binding.banners.title = banner.title
            binding.executePendingBindings()
        }
    }

}
class HomeDiffUtil : DiffUtil.ItemCallback<Banners>(){

    override fun areItemsTheSame(oldItem: Banners, newItem: Banners): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Banners, newItem: Banners): Boolean {
        return oldItem == newItem
    }

}