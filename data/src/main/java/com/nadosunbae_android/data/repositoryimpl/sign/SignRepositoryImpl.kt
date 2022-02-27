package com.nadosunbae_android.data.repositoryimpl.sign

import com.nadosunbae_android.data.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.data.mapper.classroom.*
import com.nadosunbae_android.data.mapper.classroom.SignMapper.mapperToSignEmail
import com.nadosunbae_android.data.mapper.classroom.SignMapper.mapperToSignIn
import com.nadosunbae_android.data.mapper.classroom.SignMapper.mapperToSignNickname
import com.nadosunbae_android.data.mapper.classroom.SignMapper.mapperToSignUp
import com.nadosunbae_android.domain.model.sign.*
import com.nadosunbae_android.domain.repository.sign.SignRepository

class SignRepositoryImpl(private val signDataSource : SignDataSource) : SignRepository {

    //닉네임 중복확인
    override suspend fun postSignNickname(nicknameDuplicationData: NicknameDuplicationData): NicknameDuplicationCheck {
        return SignMapper.mapperToNicknameDuplication(signDataSource.postSignNickname(
            mapperToSignNickname(nicknameDuplicationData)
        ))
    }

    //이메일 중복확인
    override suspend fun postSignEmail(emailDuplicationData: EmailDuplicationData): EmailDuplicationCheck {
        return SignMapper.mapperToEmailDuplication(signDataSource.postSignEmail(
            mapperToSignEmail(emailDuplicationData)
        ))
    }

    //학과선택 bottomsheet
    override suspend fun getFirstDepartment(universityId: Int, filter: String): SignBottomSheetItem {
        return SignMapper.mapperToMajorData(signDataSource.getFirstDepartment(universityId, filter))
    }

    //회원가입
    override suspend fun postSignUp(signUpData: SignUpData): SignUpItem {
        return SignMapper.mapperToSignUpData(signDataSource.postSignUp(
            mapperToSignUp(signUpData)
        ))
    }

    //로그인
    override suspend fun postSignIn(signInItem: SignInItem): SignInData {
        return SignMapper.mapperToSignInData(signDataSource.postSignIn(
            mapperToSignIn(signInItem)
        ))
    }
}