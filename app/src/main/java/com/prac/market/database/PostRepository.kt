package com.prac.market.database

import com.prac.market.database.hosting.HostingApiService
import com.prac.market.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository  @Inject constructor(private val hostingApiService : HostingApiService){
    suspend fun getPostList(){
        return withContext(Dispatchers.IO){
            hostingApiService.getPostList()
        }
    }
    suspend fun getPost(id : Int){
        return withContext(Dispatchers.IO){
            hostingApiService.getPost(id)
        }
    }
    suspend fun writePost(post: Post){
        return withContext(Dispatchers.IO){
            hostingApiService.writePost(post)
        }
    }
}