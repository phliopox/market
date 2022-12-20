package com.prac.market.ui.posts

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.market.databinding.FragmentPostBinding
import com.prac.market.model.Post

class PostHeadAdapter :ListAdapter<Post,PostHeadAdapter.PostHeadViewHolder>(PostDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHeadViewHolder {
       // val binding = FragmentPostBinding
    //TODO FragmentPostBinding 으로되어있지만, Item 으로 변경하여 적용해야함.!!
    }

    override fun onBindViewHolder(holder: PostHeadViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
    inner class PostHeadViewHolder(private val binding : FragmentPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(){}
    }
}
class PostDiffCallBack :DiffUtil.ItemCallback<Post>(){
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
      return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}