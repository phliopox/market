package com.prac.market.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.market.R
import com.prac.market.databinding.HomeSectionTitleBinding
import com.prac.market.model.Banners
import com.prac.market.ui.home.itemadapter.PopularProductAdapter
import com.prac.market.ui.home.itemadapter.QuestBannerAdapter
import com.prac.market.ui.home.itemadapter.SeasonalProductAdapter

class HomeSectionTitleAdapter : ListAdapter<Banners, HomeSectionTitleAdapter.HomeSectionTitleViewHolder>(
    HomeDiffUtil()
) {

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

        private val nestedQuestAdapter = QuestBannerAdapter()
        private val nestedPopularAdapter = PopularProductAdapter()
        private val nestedSeasonalProductAdapter = SeasonalProductAdapter()

        init{
            binding.homeBannerView.adapter = ConcatAdapter(nestedQuestAdapter,nestedPopularAdapter,nestedSeasonalProductAdapter)

            with(binding.homeBannerView){
                val screenWidth = resources.displayMetrics.widthPixels
                val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
                val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)

                val offset = screenWidth - pageWidth -pageMargin
                setPageTransformer{page,position ->
                    page.translationX = position*-offset
                }
                offscreenPageLimit = 3
            }
        }

        fun bind(banner: Banners) {
            binding.banners = banner
            binding.executePendingBindings()
            Log.d("HomeBanner Response : ",binding.banners.toString() )
            when (banner.title) {
                "quest" ->
                    nestedQuestAdapter.submitList(banner.banners)
                "popular_product"->
                    nestedPopularAdapter.submitList(banner.banners)
                "season_bundle" ->
                    nestedSeasonalProductAdapter.submitList(banner.banners)

            }
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