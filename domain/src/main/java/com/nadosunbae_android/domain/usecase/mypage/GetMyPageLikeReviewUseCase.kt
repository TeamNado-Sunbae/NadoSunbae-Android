package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData
import com.nadosunbae_android.domain.model.mypage.MyPageMyInfo
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageLikeReviewUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(type: String) : MyPageLikeReviewData {
        return repository.getMyPageLikeReview(type)
    }
}