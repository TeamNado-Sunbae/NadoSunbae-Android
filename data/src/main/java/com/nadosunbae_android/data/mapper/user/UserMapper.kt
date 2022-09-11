package com.nadosunbae_android.data.mapper.user

import com.nadosunbae_android.data.model.response.user.ResponseUserPost
import com.nadosunbae_android.domain.model.user.UserPostData

object UserMapper {
    fun mapperToUserPostData(responseUserPost: ResponseUserPost) : List<UserPostData> {
        return responseUserPost.data.postList.map {
            UserPostData(
                commentCount = it.commentCount,
                content = it.content,
                createdAt = it.createdAt,
                like = UserPostData.Like(
                    isLiked = it.like.isLiked,
                    likeCount = it.like.likeCount
                ),
                majorName = it.majorName,
                postId = it.postId,
                title = it.title,
                type = it.type,
                writer = UserPostData.Writer(
                    id = it.writer.id,
                    nickname = it.writer.nickname
                )
            )
        }
    }
}