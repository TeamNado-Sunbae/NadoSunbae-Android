package com.nadosunbae_android.data.datasource.remote.sign

import com.nadosunbae_android.data.api.sign.SignService
import com.nadosunbae_android.data.model.request.sign.*
import com.nadosunbae_android.data.model.response.sign.*
import javax.inject.Inject

class SignDataSourceImpl @Inject constructor(private val service: SignService) : SignDataSource {
    override suspend fun postSignNickname(requestSignNickname: RequestSignNickname): ResponseSignNickname {
        return service.postSignNickname(requestSignNickname)
    }

    override suspend fun postSignEmail(requestSignEmail: RequestSignEmail): ResponseSignEmail {
        return service.postSignEmail(requestSignEmail)
    }

    override suspend fun getFirstDepartment(
        universityId: Int,
        filter: String
    ): ResponseFirstDepartment {
        return service.getFirstDepartment(universityId, filter)
    }

    override suspend fun postSignUp(requestSignUp: RequestSignUp): ResponseSignUp {
        return service.postSignUp(requestSignUp)
    }

    override suspend fun postSignIn(requestSignIn: RequestSignIn): ResponseSignIn {
        return service.postSignIn(requestSignIn)
    }

    override suspend fun postCertificationEmail(requestCertificationEmail: RequestCertificationEmail): ResponseCertificationEmail {
        return service.postCertificationEmail(requestCertificationEmail)
    }

    override suspend fun postRenewalToken(): ResponseSignIn {
        return service.postRenewalToken()
    }
}