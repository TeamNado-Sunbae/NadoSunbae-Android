package com.nadosunbae_android.data.mapper.like

import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.data.model.request.like.RequestPostLike
import com.nadosunbae_android.data.model.response.like.ResponsePostLike

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