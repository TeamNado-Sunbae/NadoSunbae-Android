package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageReplyData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class GetMyPageReplyUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(postTypeId: Int) : MyPageReplyData {
        return repository.getMyPageReply(postTypeId)
    }
}