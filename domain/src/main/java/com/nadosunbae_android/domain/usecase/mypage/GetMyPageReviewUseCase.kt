package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageReviewData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class GetMyPageReviewUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(userId: Int) : MyPageReviewData {
        return repository.getMyPageReview(userId)
    }
}