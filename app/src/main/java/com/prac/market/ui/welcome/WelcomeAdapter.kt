package com.prac.market.ui.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.market.databinding.ItemWelcomeBinding
import com.prac.market.model.WelcomeBanner
import dagger.hilt.android.AndroidEntryPoint

class WelcomeAdapter :ListAdapter<WelcomeBanner,WelcomeAdapter.WelcomeViewHolder>(WelcomeDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder {
        val binding = ItemWelcomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WelcomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WelcomeViewHolder(private val binding: ItemWelcomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: WelcomeBanner) {
            binding.welcomeBanner = banner
            binding.executePendingBindings()
        }

    }

}

class WelcomeDiffCallBack : DiffUtil.ItemCallback<WelcomeBanner>(){
    override fun areItemsTheSame(oldItem: WelcomeBanner, newItem: WelcomeBanner): Boolean {
        return oldItem.index == newItem.index
    }

    override fun areContentsTheSame(oldItem: WelcomeBanner, newItem: WelcomeBanner): Boolean {
        return oldItem == newItem
    }

}