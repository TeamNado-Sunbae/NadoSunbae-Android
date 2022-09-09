package com.nadosunbae_android.data.datasource.remote.post

import com.nadosunbae_android.data.model.request.post.RequestPostWriteData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.post.ResponsePostData
import com.nadosunbae_android.data.model.response.post.ResponsePostDetailData
import com.nadosunbae_android.data.model.response.post.ResponsePostWriteData
import com.nadosunbae_android.domain.model.post.PostWriteData


interface PostDataSource {
    //게시글 작성
    suspend fun postWrite(requestPostWriteData: RequestPostWriteData) : Response<ResponsePostWriteData>

    //메인 게시글 데이터
    suspend fun getPost(universityId : String,majorId: String?, filter: String, sort: String, search : String?)
            : Response<List<ResponsePostData>>

    //게시글 상세 데이터
    suspend fun getPostDetail(postId : String) : Response<ResponsePostDetailData>

}