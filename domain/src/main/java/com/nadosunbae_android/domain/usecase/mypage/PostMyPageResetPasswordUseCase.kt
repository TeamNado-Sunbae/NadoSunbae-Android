package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageResetPasswordData
import com.nadosunbae_android.domain.model.mypage.MyPageResetPasswordItem
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class PostMyPageResetPasswordUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(myPageResetPasswordItem: MyPageResetPasswordItem) : MyPageResetPasswordData {
        return repository.postMyPageResetPassword(myPageResetPasswordItem)
    }
}