package com.nadosunbae_android.data.model.response.user

data class TEST(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val likeList: List<Like>
    ) {
        data class Like(
            val commentCount: Int,
            val content: String,
            val createdAt: String,
            val id: Int,
            val like: Like,
            val majorName: String,
            val title: String,
            val writer: Writer
        ) {
            data class Like(
                val isLiked: Boolean,
                val likeCount: Int
            )

            data class Writer(
                val id: Int,
                val nickname: String
            )
        }
    }
}