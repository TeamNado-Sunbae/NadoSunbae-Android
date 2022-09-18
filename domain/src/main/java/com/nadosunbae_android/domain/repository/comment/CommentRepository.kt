package com.nadosunbae_android.domain.repository.comment

import com.nadosunbae_android.domain.model.comment.CommentData
import com.nadosunbae_android.domain.model.comment.CommentParam
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    fun postComment(commentParam: CommentParam) : Flow<CommentData>
}