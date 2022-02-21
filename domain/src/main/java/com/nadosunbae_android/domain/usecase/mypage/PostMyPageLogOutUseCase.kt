package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageLogOutData
import com.nadosunbae_android.domain.model.mypage.MyPageVersionData
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class PostMyPageLogOutUseCase (private val repository: MyPageRepository) {
    suspend operator fun invoke() : MyPageLogOutData {
        return repository.postMyPageLogOut()
    }
}