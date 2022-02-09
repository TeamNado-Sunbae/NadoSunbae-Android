package com.nadosunbae_android.datasource.remote.sign

import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import com.nadosunbae_android.model.sign.NicknameDuplicationCheck
import retrofit2.Response

interface SignDataSource {
    //닉네임 중복확인
    suspend fun postSignNickname(requestSignNickname: RequestSignNickname) : ResponseSignNickname

    //이메일 중복확인
    suspend fun postSignEmail(requestSignEmail: RequestSignEmail) : ResponseSignEmail

    //학과선택 BottomSheet
    suspend fun getFirstDepartment(universityId : Int, filter : String) : ResponseFirstDepartment

    //회원가입
    suspend fun postSignUp(requestSignUp: RequestSignUp) : ResponseSignUp

    //로그인
    suspend fun postSignIn(requestSignIn: RequestSignIn) : ResponseSignIn
}
