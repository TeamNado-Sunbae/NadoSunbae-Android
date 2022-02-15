package com.nadosunbae_android.data.mapper.classroom

import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignIn
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.request.sign.RequestSignUp
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
    fun mapperToSignInData(responseSignIn: ResponseSignIn): SignInItem {
        return SignInItem(
            status = responseSignIn.status,
            success = responseSignIn.success,
            accesstoken = responseSignIn.data.accesstoken,
            user = SignInItem.User(
                email = responseSignIn.data.user.email,
                firstMajorId = responseSignIn.data.user.firstMajorId,
                firstMajorName = responseSignIn.data.user.firstMajorName,
                isReviewed = responseSignIn.data.user.isReviewed,
                secondMajorId = responseSignIn.data.user.secondMajorId,
                secondMajorName = responseSignIn.data.user.secondMajorName,
                universityId = responseSignIn.data.user.universityId,
                userId = responseSignIn.data.user.userId
            )
        )
    }


    //회원가입
    fun mapperToSignUpData(responseSignup: ResponseSignUp): SignUpItem {
        return SignUpItem(
            success = responseSignup.success,
            userId = responseSignup.data.user.userId,
            accesstoken = responseSignup.data.accesstoken
        )
    }


    //학과선택 바텀시트
    fun mapperToMajorData(responseFirstDepartment: ResponseFirstDepartment): SignBottomSheetItem {
        return SignBottomSheetItem(
            success = responseFirstDepartment.success,
            data = responseFirstDepartment.data.map {
                SignBottomSheetItem.Data(
                    isFirstMajor = it.isFirstMajor,
                    isSecondMajor = it.isSecondMajor,
                    majorId = it.majorId,
                    majorName = it.majorName
                )
            }
        )
    }



    //SignEmail
    fun mapperToSignEmail(emailDuplicationData: EmailDuplicationData): RequestSignEmail {
        return RequestSignEmail(
            email = emailDuplicationData.email
        )
    }

    //SignIn
    fun mapperToSignIn(signInData: SignInData): RequestSignIn {
        return RequestSignIn(
            email = signInData.email,
            password = signInData.password,
            deviceToken = signInData.deviceToken
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
}