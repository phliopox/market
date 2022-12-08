package com.prac.market.ui.common

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String) {
    if(!imageUrl.isNullOrEmpty()) {
        Glide.with(view)
            .load(imageUrl)
            .into(view)
    }
}

fun layoutBackground(view : View,imageUrl: String){
    if(!imageUrl.isNullOrEmpty()){
        Glide.with(view)
            .load(imageUrl)
            .into(object : CustomTarget<Drawable>(){
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    view.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}