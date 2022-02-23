package com.nadosunbae_android.domain.model.mypage

data class MyPageLikeReviewData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val likePostList: List<LikePost>
    ) {
        data class LikePost(
            val createdAt: String,
            val like: Like,
            val postId: Int,
            val tagList: List<Tag>,
            val title: String,
            val writer: Writer
        ) {
            data class Like(
                val isLiked: Boolean,
                val likeCount: Int
            )

            data class Tag(
                val tagName: String
            )

            data class Writer(
                val nickname: String,
                val writerId: Int
            )
        }
    }
}
