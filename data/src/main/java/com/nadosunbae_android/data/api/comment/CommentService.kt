package com.nadosunbae_android.data.api.comment

import com.nadosunbae_android.data.model.request.comment.RequestCommentData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.comment.ResponseCommentData
import com.nadosunbae_android.data.model.response.comment.ResponseDeleteCommentData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

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



}