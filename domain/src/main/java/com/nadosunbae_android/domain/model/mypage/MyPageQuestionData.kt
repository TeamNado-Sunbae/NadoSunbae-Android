package com.nadosunbae_android.domain.model.mypage

import com.nadosunbae_android.domain.model.like.LikeData
import java.util.*

data class MyPageQuestionData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val classroomPostList: List<ClassroomPost>
    ) {
        data class ClassroomPost(
            val commentCount: Int,
            val content: String,
            val createdAt: Date?,
            val like : Like,
            val postId: Int,
            val title: String,
            val writer: Writer
        ) {
            data class Writer(
                val nickname: String,
                val profileImageId: Int,
                val writerId: Int
            )

            data class Like(
                val isLiked : Boolean,
                val likeCount : Int
            )
        }
    }
}
