package com.nadosunbae_android.domain.repository.mypage

import com.nadosunbae_android.domain.model.mypage.MyPageModifyData
import com.nadosunbae_android.domain.model.mypage.MyPageModifyItem
import com.nadosunbae_android.domain.model.mypage.MyPageQuestionData
import com.nadosunbae_android.domain.model.mypage.MyPageMyInfo


interface MyPageRepository {
    suspend fun getMyPageQuestion(userId : Int, sort : String = "recent") : MyPageQuestionData

    suspend fun getMyPageMyInfo(userId: Int): MyPageMyInfo

    suspend fun putMyPageModify(myPageModifyItem: MyPageModifyItem) : MyPageModifyData
}