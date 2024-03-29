package com.nadosunbae_android.data.mapper.sign

import com.nadosunbae_android.data.model.request.sign.*
import com.nadosunbae_android.data.model.response.sign.*
import com.nadosunbae_android.domain.model.sign.*

object SignMapper {
    //닉네임 중복 확인
    fun mapperToNicknameDuplication(responseSignNickname: ResponseSignNickname): NicknameDuplicationCheck {
        return NicknameDuplicationCheck(
            status = responseSignNickname.status,
            success = responseSignNickname.success
        )
    }

    //이메일 중복확인
    fun mapperToEmailDuplication(responseSignEmail: ResponseSignEmail): EmailDuplicationCheck {
        return EmailDuplicationCheck(
            status = responseSignEmail.status,
            success = responseSignEmail.success
        )
    }

    //로그인
    fun mapperToSignInData(responseSignIn: ResponseSignIn): SignInData {
        return SignInData(
            status = responseSignIn.status,
            success = responseSignIn.success,
            accessToken = responseSignIn.data.accesstoken,
            refreshToken = responseSignIn.data.refreshtoken,
            user = SignInData.User(
                email = responseSignIn.data.user.email,
                firstMajorId = responseSignIn.data.user.firstMajorId,
                firstMajorName = responseSignIn.data.user.firstMajorName,
                isReviewed = responseSignIn.data.user.isReviewed,
                secondMajorId = responseSignIn.data.user.secondMajorId,
                secondMajorName = responseSignIn.data.user.secondMajorName,
                universityId = responseSignIn.data.user.universityId,
                userId = responseSignIn.data.user.userId,
                isEmailVerified = responseSignIn.data.user.isEmailVerified,
                isUserReported = responseSignIn.data.user.isUserReported,
                isReviewInappropriate = responseSignIn.data.user.isReviewInappropriate,
                message = responseSignIn.data.user.message
            )
        )
    }


    //회원가입
    fun mapperToSignUpData(responseSignup: ResponseSignUp): SignUpItem {
        return SignUpItem(
            success = responseSignup.success,
            data = SignUpItem.Data(
                user = SignUpItem.Data.User(
                    userId = responseSignup.data.user.userId,
                    createdAt = responseSignup.data.user.createdAt
                )
            )
        )
    }

    //SignEmail
    fun mapperToSignEmail(emailDuplicationData: EmailDuplicationData): RequestSignEmail {
        return RequestSignEmail(
            email = emailDuplicationData.email
        )
    }

    //SignIn
    fun mapperToSignIn(signInItem: SignInItem): RequestSignIn {
        return RequestSignIn(
            email = signInItem.email,
            password = signInItem.password,
            deviceToken = signInItem.deviceToken
        )
    }


    //SignNickname
    fun mapperToSignNickname(nicknameDuplicationData: NicknameDuplicationData): RequestSignNickname {
        return RequestSignNickname(
            nickname = nicknameDuplicationData.nickname
        )
    }

    //SignUp
    fun mapperToSignUp(signUpData: SignUpData): RequestSignUp {
        return RequestSignUp(
            email = signUpData.email,
            nickname = signUpData.nickname,
            password = signUpData.password,
            universityId = signUpData.universityId,
            firstMajorId = signUpData.firstMajorId,
            firstMajorStart = signUpData.firstMajorStart,
            secondMajorId = signUpData.secondMajorId,
            secondMajorStart = signUpData.secondMajorStart
        )
    }

    //이메일 재전송
    fun mapperToCertificationEmailData(responseCertificationEmail: ResponseCertificationEmail): CertificationEmailItem {
        return CertificationEmailItem(
            data = CertificationEmailItem.Data(
                email = responseCertificationEmail.data.email
            ),
            success = responseCertificationEmail.success
        )
    }

    fun mapperToCertificationEmailItem(certificationEmailData: CertificationEmailData): RequestCertificationEmail {
        return RequestCertificationEmail(
            email = certificationEmailData.email,
            password = certificationEmailData.password
        )
    }

    fun mapperToUnivEmail(responseUnivEmail: ResponseUnivEmail) : UnivEmailItem {
        return UnivEmailItem(
            email = responseUnivEmail.data.email
        )
    }

}