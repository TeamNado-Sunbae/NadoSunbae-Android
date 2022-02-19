package com.nadosunbae_android.domain.repository.mypage

import com.nadosunbae_android.domain.model.mypage.*


interface MyPageRepository {
    suspend fun getMyPageQuestion(userId : Int, sort : String = "recent") : MyPageQuestionData

    suspend fun getMyPageMyInfo(userId: Int): MyPageMyInfo

    suspend fun putMyPageModify(myPageModifyItem: MyPageModifyItem) : MyPageModifyData

    suspend fun getMyPagePost(type: String = "question") : MyPagePostData
}