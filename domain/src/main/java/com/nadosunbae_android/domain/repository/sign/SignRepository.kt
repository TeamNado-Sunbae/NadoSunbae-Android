package com.nadosunbae_android.domain.repository.sign

import com.nadosunbae_android.domain.model.sign.*

interface SignRepository {
    //닉네임 중복확인
    suspend fun postSignNickname(nicknameDuplicationData: NicknameDuplicationData): NicknameDuplicationCheck

    //이메일 중복확인
    suspend fun postSignEmail(emailDuplicationData: EmailDuplicationData): EmailDuplicationCheck

    //회원가입
    suspend fun postSignUp(signUpData: SignUpData): SignUpItem

    //로그인
    suspend fun postSignIn(signInItem: SignInItem): SignInData

    //이메일 재전송
    suspend fun postCertificationEmail(certificationEmailData: CertificationEmailData) : CertificationEmailItem

    //토큰 재발급 및 자동 로그인
    suspend fun postRenewalToken(): SignInData

    suspend fun getUnivEmail(universityId: Int) : UnivEmailItem

}