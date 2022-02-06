package com.nadosunbae_android.repository.sign

import com.nadosunbae_android.model.sign.*

interface SignRepository {
    //닉네임 중복확인
    suspend fun postSignNickname(nickname: String): NicknameDuplicationCheck

    //이메일 중복확인
    suspend fun postSignEmail(email: String): EmailDuplicationCheck

    //학과선택 바텀시트
    suspend fun getFirstDepartment(universityId: Int, filter: String): SignMajorBottomSheet

    //회원가입
    suspend fun postSignUp(
        email: String,
        nickname: String,
        password: String,
        universityId: Int,
        firstMajorId: Int,
        firstMajorStart: String,
        secondMajorId: Int,
        secondMajorStart: String
    ): SignUpData

    //로그인
    suspend fun postSignIn(email: String, password: String, deviceToken: String): SignInData
}