package com.nadosunbae_android.mapper.classroom

import com.nadosunbae_android.model.classroom.*
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.model.request.classroom.RequestClassRoomData
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.response.classroom.*
import java.util.*

object SignMapper {
    //response
    //닉네임 중복 확인
    fun mapperToNicknameDuplication(responseNicknameDuplicationCheck: ResponseNicknameDuplicationCheck): NicknameDuplicationCheck {
        return NicknameDuplicationCheck(
            sucess = ResponseNicknameDuplicationCheck.success
        )
    }

    //이메일 중복확인
    fun mapperToEmailDuplication(responseEmailDuplicationCheck: ResponseEmailDuplicationCheck): EmailDuplicationCheck {
        return EmailDuplicationCheck(
            sucess = responseEmailDuplicationCheck.success
        )
    }

    //학과선택 바텀시트
    fun mapperToSignMajorBottomSheet(responseSignMajorBottomSheet: ResponseSignMajorBottomSheet): SignMajorBottomSheet {
        return responseSignMajorBottomSheet.data.map {
            BottomSheetData(
                isFirstMajor = responseSignMajorBottomSheet.data.isFirstMajor,
                isSecondMajor = responseSignMajorBottomSheet.data.isSecondMajor,
                majorId = responseSignMajorBottomSheet.data.majorId,
                majorName = responseSignMajorBottomSheet.data.majorName
            )
        }
    }

    //ResponseMajorData?


    //로그인
    fun mapperToSignInData(responseSignInData: ResponseSignInData): SignInData {
        return responseSignInData.data.map {
            SignInData(
                success = responseSignInData.success,
                accesstoken = responseSignInData.data.accesstoken,
                user = responseSignInData.User(
                    email = responseSignInData.data.email,
                    firstMajorId = responseSignInData.data.firstMajorId,
                    firstMajorName = responseSignInData.data.firstMajorName,
                    isReviewed = responseSignInData.data.isReviewed,
                    secondMajorId = responseSignInData.data.secondMajorId,
                    secondMajorName = responseSignInData.data.secondMajorName,
                    universityId = responseSignInData.data.universityId,
                    userId = responseSignInData.data.userId
                )
            )
        }
    }

    //회원가입
    fun mapperToSignUpData(responseSignUpData: ResponseSignUpData): SignUpData {
        return responseSignUpData.data.map {
            SignUpData(
                success = responseSignUpData.success,
                accesstoken = responseSignUpData.accesstoken,
                user = SignUpData.User(
                    createdAt = it.createdAt,
                    userId = it.userId
                )
            )
        }
    }

    //selectable Data
    fun mapperToSelectableData(responseSelectableData: ResponseSelectableData): SelectableData {
        return SelectableData(
            id = responseSelectableData.id,
            name = responseSelectableData.name,
            isSelected = responseSelectableData.isSelected
        )
    }

    //request
    //BottomSheetData
    fun mapperToBottomSheetData(bottomSheetData: BottomSheetData) : RequestBottomSheetData {
        return RequestBottomSheetData(
            id = bottomSheetData.id,
            name = bottomSheetData.name,
            isSelected = bottomSheetData.isSelected
        )
    }

    //SignEmail
    fun mapperToSignEmail(signEmail: SignEmail) : RequestSignEmail {
        return RequestSignEmail(
            email = signEmail.email
        )
    }

    //SignIn
    fun mapperToSignIn(signInData: SignInData): RequestSignIn{
        return RequestSignIn(
            email = signInData.email,
            password = signInData.password,
            deviceToken = signInData.deviceToken
        )
    }


    //SignNickname
    fun mapperToSignNickname(signNickname: SignNickname) : RequestSignNickname {
        return RequestSignNickname(
            nickname = signNickname.nickname
        )
    }

    //SignUp
    fun mapperToSignUp(signUpData: SignUpData): RequestSignUp{
        return RequestSignUp(
            email = signUpData.email,
            nickname = signUpData.nickname,
            password = signUpData.password,
            universityId = signUpData.universityId,
            firstMajorId = signUpData.firstMajorId,
            firstMajorStart = signUpData.firstMajorStart,
            secondMajorId = signUpData.secondMajorId,
            secondMajorStart = signUpData.seconMajorStart
        )
    }


}