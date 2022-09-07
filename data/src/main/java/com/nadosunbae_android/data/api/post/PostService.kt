package com.nadosunbae_android.data.api.post

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.post.ResponsePostData
import com.nadosunbae_android.data.model.response.post.ResponsePostDetailData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {

    //게시글 조회 (추후에 변경)
    @GET("post/university/{universityId}")
    suspend fun getPost(
        @Path("universityId") universityId: String,
        @Query("majorId") majorId: String?,
        @Query("filter") filter: String,
        @Query("sort") sort: String,
        @Query("search") search: String? = ""
    ): Response<List<ResponsePostData>>

    //게시글 상세조회
    @GET("post/{postId}")
    suspend fun getPostDetail(
        @Path("postId") postId: String
    ): Response<ResponsePostDetailData>
}