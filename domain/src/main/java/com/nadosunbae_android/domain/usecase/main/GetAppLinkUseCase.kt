package com.nadosunbae_android.domain.usecase.main

import com.nadosunbae_android.domain.model.main.AppLinkData
import com.nadosunbae_android.domain.repository.main.MainRepository
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class GetAppLinkUseCase (private val repository: MainRepository) {
    suspend operator fun invoke() : AppLinkData {
        return repository.getMyPageAppLink()
    }
}