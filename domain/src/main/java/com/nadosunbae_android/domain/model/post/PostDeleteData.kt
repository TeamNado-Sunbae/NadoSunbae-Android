package com.nadosunbae_android.domain.model.post

data class PostDeleteData(
    val isDeleted: Boolean,
    val postId: Int
){
    companion object{
        val DEFAULT = PostDeleteData(
            isDeleted = false,
            postId = 0
        )
    }
}
