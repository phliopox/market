package com.prac.market.database.hosting

import com.prac.market.model.Post
import com.prac.market.model.Result
import retrofit2.Call
import retrofit2.http.*

/*base url "http://phliopox327.dothome.co.kr/" */
/*
@Path - 동적으로 경로를 사용하기 위한 어노테이션
@Query, @QueryMap - @GET 에서 사용하며 조건 파라미터를 설정
@Field, @FieldMap - @POST 에서 사용하며 조건 파라미터를 설정
@Body - 객체를 이용하여 조건 파라미터를 설정
@Header - 해더 설정*/
interface HostingApiService {
    @FormUrlEncoded
    @POST("market_signin.php")
    suspend fun addAccount(
        @Field("Email") email: String,
        @Field("Password") password: String
    ): Result

    @FormUrlEncoded
    @POST("market_login.php")
    suspend fun login(
        @Field("Email") email: String,
        @Field("Password") password: String
    ): Result


    @GET("market_board.php")
    suspend fun getPostList(): List<Post>

    @GET("post.php")
    suspend fun getPost(@Query("id") id : Int ):Post

    @POST("write_post.php")
    suspend fun writePost(@Field("post") post : Post)
}
