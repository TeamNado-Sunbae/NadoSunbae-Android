package com.nadosunbae_android.mapper.classroom

import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import com.nadosunbae_android.model.sign.*

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
    fun mapperToSignInData(responseSignIn: ResponseSignIn): SignInItem {
        return SignInItem(
            success = responseSignIn.success,
            accesstoken = responseSignIn.data.accesstoken,
            user = responseSignIn.data.user.map {
                SignInItem.User(
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
    fun mapperToSignUpData(responseSignup: ResponseSignUp): SignUpItem {
        return SignUpItem(
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

    // 학과선택 바텀시트
    fun mapperToMajorData(responseMajorData: ResponseMajorData) : SignMajorBottomSheet {
        return SignMajorBottomSheet(
            data = responseMajorData.data.map {
                SignMajorBottomSheet.Data(

                )
            }
        )
    }




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