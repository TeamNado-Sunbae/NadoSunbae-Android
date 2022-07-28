package com.nadosunbae_android.domain.usecase.like

import com.nadosunbae_android.domain.model.like.LikeData
import com.nadosunbae_android.domain.model.like.LikeItem
import com.nadosunbae_android.domain.repository.like.LikeRepository
import javax.inject.Inject

class PostLikeDataUseCase @Inject constructor(private val repository: LikeRepository) {

    suspend operator fun invoke(likeItem: LikeItem): LikeData {
        return repository.postLike(likeItem)
    }

}