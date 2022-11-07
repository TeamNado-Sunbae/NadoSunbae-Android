package com.nadosunbae_android.data.datasource.remote.comment

import com.nadosunbae_android.data.model.request.comment.RequestCommentData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.classroom.ResponseCommentUpdateData
import com.nadosunbae_android.data.model.response.comment.RequestPutCommentData
import com.nadosunbae_android.data.model.response.comment.ResponseCommentData
import com.nadosunbae_android.data.model.response.comment.ResponseDeleteCommentData

interface CommentDataSource {

    //댓글 등록
    suspend fun postComment(requestCommentData: RequestCommentData) : Response<ResponseCommentData>

    //댓글 삭제
    suspend fun deleteComment(commentId : String) : ResponseDeleteCommentData

    //댓글 수정
    suspend fun putComment(commentId: String, requestPutCommentData: RequestPutCommentData): ResponseCommentUpdateData
}