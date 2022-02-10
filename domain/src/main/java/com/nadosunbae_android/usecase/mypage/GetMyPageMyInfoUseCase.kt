package com.nadosunbae_android.usecase.mypage

import com.nadosunbae_android.model.mypage.MyPageMyInfo
import com.nadosunbae_android.repository.mypage.MyPageRepository

class GetMyPageMyInfoUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke() : MyPageMyInfo{
        return repository.getMyPageMyInfo()
    }
}