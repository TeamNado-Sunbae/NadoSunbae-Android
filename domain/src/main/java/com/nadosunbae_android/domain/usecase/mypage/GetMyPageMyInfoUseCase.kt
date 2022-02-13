package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageMyInfo
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageMyInfoUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(userId: Int) : MyPageMyInfo{
        return repository.getMyPageMyInfo(userId)
    }
}