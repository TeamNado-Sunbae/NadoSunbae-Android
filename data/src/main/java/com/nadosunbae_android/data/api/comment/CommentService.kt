package com.nadosunbae_android.data.api.comment

import com.nadosunbae_android.data.model.request.comment.RequestCommentData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.classroom.ResponseCommentUpdateData
import com.nadosunbae_android.data.model.response.comment.RequestPutCommentData
import com.nadosunbae_android.data.model.response.comment.ResponseCommentData
import com.nadosunbae_android.data.model.response.comment.ResponseDeleteCommentData
import retrofit2.http.*

interface CommentService {

    //댓글 등록
    @POST("comment")
    suspend fun postComment(
        @Body requestCommentData: RequestCommentData
    ): Response<ResponseCommentData>

    //댓글 삭제
    @DELETE("comment/{commentId}")
    suspend fun deleteComment(
        @Path ("commentId") commentId :String
    ) : ResponseDeleteCommentData

    // 댓글 수정
    @PUT("comment/{commentId}")
    suspend fun putComment(
        @Path("commentId") commentId: String,
        @Body requestPutCommentData: RequestPutCommentData
    ): ResponseCommentUpdateData


}