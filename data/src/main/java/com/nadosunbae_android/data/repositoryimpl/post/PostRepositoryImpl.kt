package com.nadosunbae_android.data.repositoryimpl.post

import com.nadosunbae_android.data.datasource.remote.post.PostDataSource
import com.nadosunbae_android.data.model.request.post.toEntity
import com.nadosunbae_android.data.model.response.post.toEntity
import com.nadosunbae_android.domain.model.post.PostData
import com.nadosunbae_android.domain.model.post.PostDetailData
import com.nadosunbae_android.domain.model.post.PostWriteData
import com.nadosunbae_android.domain.model.post.PostWriteParam
import com.nadosunbae_android.domain.repository.post.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepositoryImpl(private val dataSource: PostDataSource) : PostRepository {
    override fun postWrite(postWriteParam: PostWriteParam): Flow<PostWriteData> = flow {
        emit(
            dataSource.postWrite(
                postWriteParam.toEntity()
            ).data.toEntity()
        )
    }.flowOn(Dispatchers.IO)

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


    //게시글 상세 조회
    override fun getPostDetail(postId: String): Flow<PostDetailData> {
        return flow {
            emit(
                dataSource.getPostDetail(postId)
                    .data.toEntity()
            )
        }.flowOn(Dispatchers.IO)
    }
}