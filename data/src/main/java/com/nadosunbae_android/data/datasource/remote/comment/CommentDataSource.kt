package com.nadosunbae_android.data.datasource.remote.comment

import com.nadosunbae_android.data.model.request.comment.RequestCommentData
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.comment.ResponseCommentData

interface CommentDataSource {

    suspend fun postComment(requestCommentData: RequestCommentData) : Response<ResponseCommentData>
}