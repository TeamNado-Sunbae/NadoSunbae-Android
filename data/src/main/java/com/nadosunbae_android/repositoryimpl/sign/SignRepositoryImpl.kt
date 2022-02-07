package com.nadosunbae_android.repositoryimpl.sign

import com.nadosunbae_android.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.mapper.classroom.SignMapper
import com.nadosunbae_android.model.sign.*
import com.nadosunbae_android.repository.sign.SignRepository

class SignRepositoryImpl(private val signDataSource : SignDataSource) : SignRepository {

    //닉네임 중복확인
    override suspend fun postSignNickname(nicknameDuplicationData: NicknameDuplicationData): NicknameDuplicationCheck {
        return SignMapper.mapperToNicknameDuplication(signDataSource.postSignNickname(
            SignMapper.mapperToSignNickname(nicknameDuplicationData)
        ))
    }

    //이메일 중복확인
    override suspend fun postSignEmail(emailDuplicationData: EmailDuplicationData): EmailDuplicationCheck {
        return SignMapper.mapperToEmailDuplication(signDataSource.postSignEmail(
            SignMapper.mapperToSignEmail(emailDuplicationData)
        ))
    }

    //학과선택 bottomsheet
    override suspend fun getFirstDepartment(universityId: Int, filter: String): SignMajorBottomSheet {
        //return SignMapper.mapperToBottomSheetData(signDataSource.getFirstDepartment(universityId, filter))

    }

    //회원가입
    override suspend fun postSignUp(signUpData: SignUpData): SignUpItem {
        return SignMapper.mapperToSignUpData(signDataSource.postSignUp(
            SignMapper.mapperToSignUp(signUpData)
        ))
    }

    //로그인
    override suspend fun postSignIn(signInData: SignInData): SignInItem {
        return SignMapper.mapperToSignInData(signDataSource.postSignIn(
            SignMapper.mapperToSignIn(signInData)
        ))
    }
}