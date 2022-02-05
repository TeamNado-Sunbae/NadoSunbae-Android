package com.nadosunbae_android.data.model.response.review

data class ResponsePutReviewData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val backgroundImage: BackgroundImage,
        val like: Like,
        val post: Post,
        val writer: Writer
    ) {
        data class BackgroundImage(
            val imageId: Int,
            val imageUrl: String
        )

        data class Like(
            val isLiked: Boolean,
            val likeCount: String
        )

        data class Post(
            val contentList: List<Content>,
            val createdAt: String,
            val oneLineReview: String,
            val postId: Int,
            val updatedAt: String
        ) {
            data class Content(
                val content: String,
                val title: String
            )
        }

        data class Writer(
            val firstMajorName: String,
            val firstMajorStart: String,
            val nickname: String,
            val profileImageId: Int,
            val secondMajorName: String,
            val secondMajorStart: String,
            val writerId: Int
        )
    }
}