package com.nadosunbae_android.data.repositoryimpl.comment

import com.nadosunbae_android.data.datasource.remote.comment.CommentDataSource
import com.nadosunbae_android.data.model.request.comment.toEntity
import com.nadosunbae_android.data.model.response.comment.toEntity
import com.nadosunbae_android.domain.model.comment.CommentData
import com.nadosunbae_android.domain.model.comment.CommentParam
import com.nadosunbae_android.domain.model.comment.DeleteCommentData
import com.nadosunbae_android.domain.repository.comment.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val dataSource : CommentDataSource)
    : CommentRepository{
    override fun postComment(commentParam: CommentParam): Flow<CommentData> {
        return flow {
            emit(dataSource.postComment(
                commentParam.toEntity()
            ).data.toEntity())
        }.flowOn(Dispatchers.IO)
    }

    override fun deleteComment(commentId: String): Flow<DeleteCommentData> {
        return flow {
            emit(dataSource.deleteComment(commentId).toEntity())
        }.flowOn(Dispatchers.IO)
    }
}