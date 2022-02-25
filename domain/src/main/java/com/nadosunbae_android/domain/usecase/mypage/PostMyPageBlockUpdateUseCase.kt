package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateData
import com.nadosunbae_android.domain.model.mypage.MyPageBlockUpdateItem
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class PostMyPageBlockUpdateUseCase (private val repository: MyPageRepository) {
    suspend operator fun invoke(myPageBlockUpdateItem: MyPageBlockUpdateItem) : MyPageBlockUpdateData {
        return repository.postMyPageBlockUpdate(myPageBlockUpdateItem)
    }
}