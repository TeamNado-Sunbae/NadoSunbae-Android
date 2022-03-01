package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageAppLinkData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetMyPageAppLinkUseCase (private val repository: MyPageRepository) {
    suspend operator fun invoke() : MyPageAppLinkData {
        return repository.getMyPageAppLink()
    }
}