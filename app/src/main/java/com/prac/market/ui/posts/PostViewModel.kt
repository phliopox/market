package com.prac.market.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prac.market.database.PostRepository
import com.prac.market.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel(){
    private val _postList =MutableLiveData<List<Post>>()
    val postList : LiveData<List<Post>> = _postList

    init {
        getPostList()
    }

    private fun getPostList(){
        viewModelScope.launch {
            val result = postRepository.getPostList()
            _postList.value =result

        }
    }

}