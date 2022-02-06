package com.nadosunbae_android.repositoryimpl.sign

import com.nadosunbae_android.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.mapper.classroom.SignMapper
import com.nadosunbae_android.model.sign.*
import com.nadosunbae_android.repository.sign.SignRepository

class SignRepositoryImpl(private val signDataSource : SignDataSource) : SignRepository {

    //닉네임 중복확인
    override suspend fun postSignNickname(nickname: String): NicknameDuplicationCheck {
        return SignMapper.mapperToNicknameDuplication(signDataSource.postSignNickname(
            SignMapper.mapperToSignNickname(nickname)
        ))
    }

    //이메일 중복확인
    override suspend fun postSignEmail(email: String): EmailDuplicationCheck {
        return SignMapper.mapperToEmailDuplication(signDataSource.postSignEmail(
            SignMapper.mapperToSignEmail(email)
        ))
    }

    //학과선택 bottomsheet
    override suspend fun getFirstDepartment(universityId: Int, filter: String): BottomSheetData {
        return SignMapper.mapperToBottomSheetData(signDataSource.getFirstDepartment(universityId, filter))
    }

    override suspend fun postSignUp(
        email: String,
        nickname: String,
        password: String,
        universityId: Int,
        firstMajorId: Int,
        firstMajorStart: String,
        secondMajorId: Int,
        secondMajorStart: String
    ): SignUpData {
        return SignMapper.mapperToSignUpData(signDataSource.postSignUp(
            SignMapper.mapperToSignUp()
        ))
    }

    override suspend fun postSignIn(
        email: String,
        password: String,
        deviceToken: String
    ): SignInData {
        return SignMapper.mapperToSignInData(signDataSource.postSignIn(
            SignMapper.mapperToSignIn()
        ))
    }
}