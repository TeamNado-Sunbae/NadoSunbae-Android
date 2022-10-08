package com.nadosunbae_android.data.datasource.remote.post

import com.nadosunbae_android.data.api.post.PostService
import com.nadosunbae_android.data.model.request.post.RequestPostUpdateData
import com.nadosunbae_android.data.model.request.post.RequestPostWriteData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.post.*
import com.nadosunbae_android.domain.model.post.PostWriteData
import javax.inject.Inject



class PostDataSourceImpl @Inject constructor(private val service: PostService) : PostDataSource {

    override suspend fun postWrite(requestPostWriteData: RequestPostWriteData): Response<ResponsePostWriteData> {
        return service.postWrite(requestPostWriteData)
    }

    override suspend fun deletePost(postId: String): Response<ResponsePostDeleteData> {
        return service.deletePost(postId)
    }

    override suspend fun getPost(
        universityId: Int,
        majorId: String?,
        filter: String,
        sort: String,
        search: String?
    ): Response<List<ResponsePostData>> {
        return service.getPost(universityId, majorId, filter, sort, search)
    }

    override suspend fun getPostDetail(postId: String): Response<ResponsePostDetailData> {
        return service.getPostDetail(postId)
    }

    override suspend fun putPostUpdate(
        postId: String,
        requestPostUpdateData: RequestPostUpdateData
    ): ResponsePostUpdateData {
        return service.putPostUpdate(postId, requestPostUpdateData)
    }

}