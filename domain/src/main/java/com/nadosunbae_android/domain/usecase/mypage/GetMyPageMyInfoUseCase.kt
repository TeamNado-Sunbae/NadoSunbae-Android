package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.data.model.mypage.MyPageMyInfo
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageMyInfoUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke() : MyPageMyInfo{
        return repository.getMyPageMyInfo()
    }
}