package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class GetMyPageLikeReviewUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(type: String) : MyPageLikeReviewData {
        return repository.getMyPageLikeReview(type)
    }
}