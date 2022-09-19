package com.nadosunbae_android.domain.repository.comment

import com.nadosunbae_android.domain.model.comment.CommentData
import com.nadosunbae_android.domain.model.comment.CommentParam
import com.nadosunbae_android.domain.model.comment.DeleteCommentData
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    //댓글 등록
    fun postComment(commentParam: CommentParam) : Flow<CommentData>

    //댓글 삭제
    fun deleteComment(commentId : String) : Flow<DeleteCommentData>
}