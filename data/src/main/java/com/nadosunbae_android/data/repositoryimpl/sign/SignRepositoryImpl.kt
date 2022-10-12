package com.nadosunbae_android.data.repositoryimpl.sign

import com.nadosunbae_android.data.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.data.mapper.sign.SignMapper
import com.nadosunbae_android.data.mapper.sign.SignMapper.mapperToSignEmail
import com.nadosunbae_android.data.mapper.sign.SignMapper.mapperToSignIn
import com.nadosunbae_android.data.mapper.sign.SignMapper.mapperToSignNickname
import com.nadosunbae_android.data.mapper.sign.SignMapper.mapperToSignUp
import com.nadosunbae_android.domain.model.sign.*
import com.nadosunbae_android.domain.repository.sign.SignRepository
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(private val signDataSource : SignDataSource) : SignRepository {

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

    override suspend fun postCertificationEmail(certificationEmailItem: CertificationEmailData): CertificationEmailItem {
        return SignMapper.mapperToCertificationEmailData(signDataSource.postCertificationEmail(
            SignMapper.mapperToCertificationEmailItem(certificationEmailItem)
        ))
    }

    override suspend fun postRenewalToken(): SignInData {
        return SignMapper.mapperToSignInData(signDataSource.postRenewalToken())
    }

    override suspend fun getUnivEmail(universityId: Int): UnivEmailItem {
        return SignMapper.mapperToUnivEmail(signDataSource.getUnivEmail(universityId))
    }
}