package com.nadosunbae_android.data.api.comment

import com.nadosunbae_android.data.model.request.comment.RequestCommentData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.comment.ResponseCommentData
import retrofit2.http.Body
import retrofit2.http.POST

interface CommentService {

    @POST("comment")
    suspend fun postComment(
        @Body requestCommentData: RequestCommentData
    ): Response<ResponseCommentData>
}