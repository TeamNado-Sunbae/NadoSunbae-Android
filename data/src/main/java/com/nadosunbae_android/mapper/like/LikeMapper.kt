package com.nadosunbae_android.mapper.like

import com.nadosunbae_android.model.like.LikeData
import com.nadosunbae_android.model.like.LikeItem
import com.nadosunbae_android.model.request.like.RequestPostLike
import com.nadosunbae_android.model.response.like.ResponsePostLike

object LikeMapper {

    // 좋아요 request
    fun mapperToLikeItem(likeItem: LikeItem): RequestPostLike {
        return RequestPostLike(
            postId = likeItem.postId,
            postTypeId = likeItem.postTypeId
        )
    }

    // 좋아요 response
    fun mapperToLikeData(responsePostLike: ResponsePostLike): LikeData {
        return LikeData(
            isLiked = responsePostLike.data.isLiked,
            postId = responsePostLike.data.postId
        )
    }

}