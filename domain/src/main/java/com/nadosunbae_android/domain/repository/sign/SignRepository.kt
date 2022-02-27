package com.nadosunbae_android.domain.repository.sign

import com.nadosunbae_android.domain.model.sign.*

interface SignRepository {
    //닉네임 중복확인
    suspend fun postSignNickname(nicknameDuplicationData: NicknameDuplicationData): NicknameDuplicationCheck

    //이메일 중복확인
    suspend fun postSignEmail(emailDuplicationData: EmailDuplicationData): EmailDuplicationCheck

    //학과선택 바텀시트
    suspend fun getFirstDepartment(universityId: Int, filter: String): SignBottomSheetItem

    //회원가입
    suspend fun postSignUp(signUpData: SignUpData): SignUpItem

    //로그인
    suspend fun postSignIn(signInItem: SignInItem): SignInData

    //토큰 재발급
    suspend fun postRenewalToken(refreshToken: String): RenewalTokenData
}