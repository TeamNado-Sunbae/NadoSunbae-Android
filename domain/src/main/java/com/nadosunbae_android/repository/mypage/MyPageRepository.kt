package com.nadosunbae_android.repository.mypage

import com.nadosunbae_android.model.mypage.MyPageMyInfo
import com.nadosunbae_android.model.mypage.MyPageQuestionData

interface MyPageRepository {
    suspend fun getMyPageQuestion(userId : Int, sort : String) : MyPageQuestionData

    suspend fun getMyPageMyInfo(): MyPageMyInfo
}