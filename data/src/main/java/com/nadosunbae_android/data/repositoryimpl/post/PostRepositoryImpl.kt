package com.nadosunbae_android.data.repositoryimpl.post

import com.nadosunbae_android.data.datasource.remote.post.PostDataSource
import com.nadosunbae_android.data.model.request.post.toEntity
import com.nadosunbae_android.data.model.response.post.toEntity
import com.nadosunbae_android.domain.model.post.*
import com.nadosunbae_android.domain.repository.post.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val dataSource: PostDataSource) : PostRepository {

    //게시글 조회
    override fun getPost(
        universityId: String,
        majorId: String?,
        filter: String,
        sort: String,
        search: String?
    ): Flow<List<PostData>> = flow {
        emit(dataSource.getPost(universityId, majorId, filter, sort, search)
            .data
            .map { it.toEntity() })
    }.flowOn(Dispatchers.IO)

    override fun deletePost(postId: String): Flow<PostDeleteData> = flow {
        emit(dataSource.deletePost(postId).data.toEntity())
    }.flowOn(Dispatchers.IO)

    //게시글 상세 조회
    override fun getPostDetail(postId: String): Flow<PostDetailData> {
        return flow {
            emit(
                dataSource.getPostDetail(postId)
                    .data.toEntity()
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun postWrite(postWriteParam: PostWriteParam): Flow<PostWriteData> {
        return flow{
            emit(dataSource.postWrite(postWriteParam.toEntity())
                .data.toEntity())
        }.flowOn(Dispatchers.IO)
    }

    override fun putPostUpdate(
        postId: String,
        postUpdateParam: PostUpdateParam
    ): Flow<PostUpdateData> {
        return flow<PostUpdateData> {
            emit(dataSource.putPostUpdate(postId, postUpdateParam.toEntity()).toEntity())
        }.flowOn(Dispatchers.IO)
    }
}