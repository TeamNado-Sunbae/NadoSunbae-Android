package com.nadosunbae_android.domain.repository.mypage

import com.nadosunbae_android.data.model.mypage.MyPageMyInfo
import com.nadosunbae_android.data.model.mypage.MyPageQuestionData

interface MyPageRepository {
    suspend fun getMyPageQuestion(userId : Int, sort : String) : MyPageQuestionData

    suspend fun getMyPageMyInfo(): MyPageMyInfo
}