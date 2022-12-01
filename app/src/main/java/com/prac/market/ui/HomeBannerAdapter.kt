package com.prac.market.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.market.databinding.ItemDailyQuestBinding
import com.prac.market.model.Banner
import com.prac.market.model.Banners

class HomeBannerAdapter : ListAdapter<Banner, HomeBannerAdapter.HomeBannerViewHolder>(BannerDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        val binding =
            ItemDailyQuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class HomeBannerViewHolder(private val binding: ItemDailyQuestBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: Banner) {
            binding.banner = banner
            binding.executePendingBindings()
        }
    }
}
class BannerDiffCallBack : DiffUtil.ItemCallback<Banner>(){
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

}