package com.nadosunbae_android.data.datasource.remote.post

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.post.ResponsePostDetailData


interface PostDataSource {
    suspend fun getPostDetail(postId : String) : Response<ResponsePostDetailData>

}