package com.nadosunbae_android.data.datasource.remote.post

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.post.ResponsePostData
import com.nadosunbae_android.data.model.response.post.ResponsePostDetailData


interface PostDataSource {

    //메인 게시글 데이터
    suspend fun getPost(universityId : String,majorId: String?, filter: String, sort: String, search : String?)
            : Response<List<ResponsePostData>>

    suspend fun getPostDetail(postId : String) : Response<ResponsePostDetailData>

}