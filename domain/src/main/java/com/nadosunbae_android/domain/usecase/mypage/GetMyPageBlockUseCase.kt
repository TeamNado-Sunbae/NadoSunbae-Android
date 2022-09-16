package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageBlockData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class GetMyPageBlockUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke() : MyPageBlockData {
        return repository.getMyPageBlock()
    }
}