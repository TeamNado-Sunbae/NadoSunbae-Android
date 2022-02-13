package com.nadosunbae_android.domain.repository.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageMyInfo
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData

interface MyPageRepository {
    suspend fun getMyPageQuestion(userId : Int, sort : String) : MyPageQuestionData

    suspend fun getMyPageMyInfo(userId: Int): MyPageMyInfo
}