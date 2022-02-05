package com.nadosunbae_android.usecase.like

import com.nadosunbae_android.model.like.LikeData
import com.nadosunbae_android.model.like.LikeItem
import com.nadosunbae_android.repository.like.LikeRepository

class PostLikeDataUseCase(private val repository: LikeRepository) {

    suspend operator fun invoke(likeItem: LikeItem): LikeData {
        return repository.postLike(likeItem)
    }

}