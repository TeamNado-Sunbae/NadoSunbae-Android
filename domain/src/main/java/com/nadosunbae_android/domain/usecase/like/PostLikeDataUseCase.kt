package com.nadosunbae_android.domain.usecase.like

import com.nadosunbae_android.data.model.like.LikeData
import com.nadosunbae_android.data.model.like.LikeItem
import com.nadosunbae_android.domain.repository.like.LikeRepository

class PostLikeDataUseCase(private val repository: LikeRepository) {

    suspend operator fun invoke(likeItem: LikeItem): LikeData {
        return repository.postLike(likeItem)
    }

}