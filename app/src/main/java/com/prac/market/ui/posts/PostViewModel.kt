package com.prac.market.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prac.market.database.PostRepository
import com.prac.market.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(postRepository: PostRepository) : ViewModel(){
    private val _post =MutableLiveData<Post>()
    val post : LiveData<Post> = _post


}