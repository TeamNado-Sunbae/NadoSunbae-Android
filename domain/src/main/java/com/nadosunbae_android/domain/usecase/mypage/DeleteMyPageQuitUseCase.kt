package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageQuitData
import com.nadosunbae_android.domain.model.mypage.MyPageQuitItem
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class DeleteMyPageQuitUseCase (private val repository: MyPageRepository) {
    suspend operator fun invoke(myPageQuitItem: MyPageQuitItem) : MyPageQuitData {
        return repository.deleteMyPageQuit(myPageQuitItem)
    }
}