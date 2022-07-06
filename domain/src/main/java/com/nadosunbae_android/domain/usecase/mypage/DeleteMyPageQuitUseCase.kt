package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageQuitData
import com.nadosunbae_android.domain.model.mypage.MyPageQuitItem
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class DeleteMyPageQuitUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(myPageQuitItem: MyPageQuitItem) : MyPageQuitData {
        return repository.deleteMyPageQuit(myPageQuitItem)
    }
}