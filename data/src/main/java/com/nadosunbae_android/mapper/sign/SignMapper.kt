package com.nadosunbae_android.mapper.classroom

import androidx.lifecycle.Transformations.map
import com.nadosunbae_android.model.classroom.*
import com.nadosunbae_android.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.model.request.classroom.RequestClassRoomData
import com.nadosunbae_android.model.request.classroom.RequestClassRoomPostData
import com.nadosunbae_android.model.request.classroom.RequestQuestionCommentWriteData
import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.classroom.*
import com.nadosunbae_android.model.response.sign.*
import com.nadosunbae_android.model.sign.*
import kotlinx.coroutines.channels.ChannelResult.Companion.success
import retrofit2.Response.success
import java.util.*
import kotlin.Result.Companion.success

object SignMapper {
    //response
    //닉네임 중복 확인
    fun mapperToNicknameDuplication(responseSignnNickname: ResponseSignNickname): NicknameDuplicationCheck {
        return NicknameDuplicationCheck(
            success = responseSignnNickname.success,
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
                    email = responseSignIn.data.user.email,
                    firstMajorId = responseSignIn.data.user.firstMajorId,
                    firstMajorName = responseSignIn.data.user.firstMajorName,
                    isReviewed = responseSignIn.data.user.isReviewed,
                    secondMajorId = responseSignIn.data.user.secondMajorId,
                    secondMajorName = responseSignIn.data.user.secondMajorName,
                    universityId = responseSignIn.data.user.universityId,
                    userId = responseSignIn.data.user.userId
                )
            }
        )
    }


    //회원가입
    fun mapperToSignUpData(responseSignup: ResponseSignUp): SignUpData {
        return SignUpData(
            success = responseSignup.success,
            userId = responseSignup.userId,
            accesstoken = responseSignup.accesstoken
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

//    fun mapperToFirstDepartment(responseFirstDepartment: ResponseFirstDepartment) : ResponseFirstDepartment {
//
//    }
//
//    fun mapperToMajorData(responseMajorData: ResponseMajorData) : ResponseMajorData {
//        return ResponseMajorData(
//            data = responseMajorData.data,
//            success = responseMajorData.success
//        )
//    }

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
    fun mapperToSignEmail(requestSignEmail: RequestSignEmail): RequestSignEmail {
        return RequestSignEmail(
            email = requestSignEmail.email
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
    fun mapperToSignNickname(requestSignNickname: RequestSignNickname): RequestSignNickname {
        return RequestSignNickname(
            nickname = requestSignNickname.nickname
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