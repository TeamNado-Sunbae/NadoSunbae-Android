package com.nadosunbae_android.domain.usecase.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageModifyData
import com.nadosunbae_android.domain.model.mypage.MyPageModifyItem
import com.nadosunbae_android.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class PutMyPageModifyUseCase @Inject constructor(private val repository: MyPageRepository) {
    suspend operator fun invoke(myPageModifyItem: MyPageModifyItem) : MyPageModifyData {
        return repository.putMyPageModify(myPageModifyItem)
    }
}