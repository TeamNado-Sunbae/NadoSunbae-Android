package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageModifyData
import com.nadosunbae_android.domain.model.mypage.MyPageModifyItem
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository

class PutMyPageModifyUseCase(private val repository: MyPageRepository) {
    suspend operator fun invoke(myPageModifyItem: MyPageModifyItem) : MyPageModifyData {
        return repository.putMyPageModify(myPageModifyItem)
    }
}