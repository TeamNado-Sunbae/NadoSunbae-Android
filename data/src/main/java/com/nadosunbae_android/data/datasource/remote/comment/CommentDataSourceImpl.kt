package com.nadosunbae_android.data.datasource.remote.comment

import com.nadosunbae_android.data.api.comment.CommentService
import com.nadosunbae_android.data.model.request.comment.RequestCommentData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.classroom.ResponseCommentUpdateData
import com.nadosunbae_android.data.model.response.comment.RequestPutCommentData
import com.nadosunbae_android.data.model.response.comment.ResponseCommentData
import com.nadosunbae_android.data.model.response.comment.ResponseDeleteCommentData
import javax.inject.Inject

class CommentDataSourceImpl @Inject constructor(private val service : CommentService)
    : CommentDataSource{
    override suspend fun postComment(requestCommentData: RequestCommentData): Response<ResponseCommentData> {
        return service.postComment(requestCommentData)
    }

    override suspend fun deleteComment(commentId: String): ResponseDeleteCommentData {
        return service.deleteComment(commentId)
    }

    override suspend fun putComment(commentId: String, requestPutCommentData: RequestPutCommentData): ResponseCommentUpdateData {
        return service.putComment(commentId, requestPutCommentData)
    }
}