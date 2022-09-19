package com.nadosunbae_android.domain.model.comment

data class DeleteCommentData(
    val success : Boolean,
    val commentId: Int,
    val isDeleted: Boolean
){
    companion object{
        val DEFAULT = DeleteCommentData(
            success = false,
            commentId = 0,
            isDeleted = false
        )
    }
}
