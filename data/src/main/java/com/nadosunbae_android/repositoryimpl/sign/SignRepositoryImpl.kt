package com.nadosunbae_android.repositoryimpl.sign

import com.nadosunbae_android.api.ApiService
import com.nadosunbae_android.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.datasource.remote.sign.SignDataSourceImpl
import com.nadosunbae_android.mapper.classroom.SignMapper
import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import com.nadosunbae_android.model.sign.*
import com.nadosunbae_android.repository.sign.SignRepository
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class SignRepositoryImpl(private val signDataSource : SignDataSource) : SignRepository {

    //닉네임 중복확인
    override suspend fun postSignNickname(signNickname: RequestSignNickname): NicknameDuplicationCheck {
        return SignMapper.mapperToNicknameDuplication(signDataSource.postSignNickname(
            SignMapper.mapperToSignNickname(signNickname)
        ))
    }

    //이메일 중복확인
    override suspend fun postSignEmail(signEmail: RequestSignEmail): EmailDuplicationCheck{
        return SignMapper.mapperToEmailDuplication(signDataSource.postSignEmail(
            SignMapper.mapperToSignEmail(signEmail)
        ))
    }

    //학과선택 bottomsheet
    override suspend fun getFirstDepartment(universityId: Int, filter: String): BottomSheetData {
        return SignMapper.mapperToBottomSheetData(signDataSource.getFirstDepartment(universityId, filter))
    }

    //로그인
    override suspend fun postSignIn(signInData: SignInData): SignInData {
        return SignMapper.mapperToSignInData(signInData.postSignIn(
            SignMapper.mapperToSignIn(signInData)
        ))
    }

    //회원가입
    override suspend fun postSignUp(signUpData: SignUpData): SignUpData {
        return SignMapper.mapperToSignUpData(signUpData.postSignUp(
            SignMapper.mapperToSignUp(signUpData)
        ))
    }
}