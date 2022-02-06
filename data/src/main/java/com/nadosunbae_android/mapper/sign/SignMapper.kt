package com.nadosunbae_android.mapper.classroom

import com.nadosunbae_android.model.classroom.*
import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.classroom.*
import com.nadosunbae_android.model.response.sign.*
import com.nadosunbae_android.model.sign.*
import java.util.*

object SignMapper {
    //response
    //닉네임 중복 확인
    fun mapperToNicknameDuplication(responseSignNickname: ResponseSignNickname): NicknameDuplicationCheck {
        return NicknameDuplicationCheck(
            success = responseSignNickname.success,
        )
    }

    //이메일 중복확인
    fun mapperToEmailDuplication(responseSignEmail: ResponseSignEmail): EmailDuplicationCheck {
        return EmailDuplicationCheck(
            success = responseSignEmail.success
        )
    }

    //로그인
    fun mapperToSignInData(responseSignIn: ResponseSignIn): SignInData {
        return SignInData(
            success = responseSignIn.success,
            accesstoken = responseSignIn.data.accesstoken,
            user = responseSignIn.data.user.map {
                SignInData.User(
                    email = it.email,
                    firstMajorId = it.firstMajorId,
                    firstMajorName = it.firstMajorName,
                    isReviewed = it.isReviewed,
                    secondMajorId = it.secondMajorId,
                    secondMajorName = it.secondMajorName,
                    universityId = it.universityId,
                    userId = it.userId
                )
            }
        )
    }


    //회원가입
    fun mapperToSignUpData(responseSignup: ResponseSignUp): SignUpData {
        return SignUpData(
            success = responseSignup.success,
            userId = responseSignup.data.user.userId,
            accesstoken = responseSignup.data.accesstoken
        )
    }

    //selectable Data
    fun mapperToSelectableData(responseSelectableData: SelectableData): SelectableData {
        return SelectableData(
            id = responseSelectableData.id,
            name = responseSelectableData.name,
            isSelected = responseSelectableData.isSelected
        )
    }

    /*
    fun mapperToMajorData(responseMajorData: ResponseMajorData) : ResponseMajorData {
        return ResponseMajorData(
            data = responseMajorData.data,
            success = responseMajorData.success
        )
    }

     */


//request
//BottomSheetData
    fun mapperToBottomSheetData(bottomSheetData: BottomSheetData): BottomSheetData {
        return BottomSheetData(
            id = bottomSheetData.id,
            name = bottomSheetData.name,
            isSelected = bottomSheetData.isSelected
        )
    }

    //SignEmail
    fun mapperToSignEmail(email: String): RequestSignEmail {
        return RequestSignEmail(
            email = email
        )
    }

    //SignIn
    fun mapperToSignIn(requestSignIn: RequestSignIn): RequestSignIn {
        return RequestSignIn(
            email = requestSignIn.email,
            password = requestSignIn.password,
            deviceToken = requestSignIn.deviceToken
        )
    }


    //SignNickname
    fun mapperToSignNickname(nickname: String): RequestSignNickname {
        return RequestSignNickname(
            nickname = nickname
        )
    }

    //SignUp
    fun mapperToSignUp(requestSignUpData: RequestSignUp): RequestSignUp {
        return RequestSignUp(
            email = requestSignUpData.email,
            nickname = requestSignUpData.nickname,
            password = requestSignUpData.password,
            universityId = requestSignUpData.universityId,
            firstMajorId = requestSignUpData.firstMajorId,
            firstMajorStart = requestSignUpData.firstMajorStart,
            secondMajorId = requestSignUpData.secondMajorId,
            secondMajorStart = requestSignUpData.secondMajorStart
        )
    }
}