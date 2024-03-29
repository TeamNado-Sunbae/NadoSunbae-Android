package com.nadosunbae_android.domain.repository.mypage

import com.nadosunbae_android.domain.model.main.AppLinkData
import com.nadosunbae_android.domain.model.mypage.*


interface MyPageRepository {
    suspend fun putMyPageModify(myPageModifyItem: MyPageModifyItem) : MyPageModifyData

    suspend fun getMyPageVersion() : MyPageVersionData

    suspend fun postMyPageLogOut() : MyPageLogOutData

    suspend fun getMyPageBlock() : MyPageBlockData

    suspend fun postMyPageBlockUpdate(myPageBlockUpdateItem: MyPageBlockUpdateItem): MyPageBlockUpdateData

    suspend fun postMyPageResetPassword(myPageResetPasswordItem: MyPageResetPasswordItem) : MyPageResetPasswordData

    suspend fun deleteMyPageQuit(myPageQuitItem: MyPageQuitItem) : MyPageQuitData

}