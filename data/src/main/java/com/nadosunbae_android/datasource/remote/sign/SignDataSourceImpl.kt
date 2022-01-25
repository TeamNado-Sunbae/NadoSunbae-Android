package com.nadosunbae_android.datasource.remote.sign

import com.nadosunbae_android.api.sign.SignService
import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*

class SignDataSourceImpl(private val service : SignService) : SignDataSource {
    override suspend fun postSignNickname(requestSignNickname: RequestSignNickname) : ResponseSignNickname {
        return service.postSignNickname(requestSignNickname)
    }

    override suspend fun postSignEmail(requestSignEmail: RequestSignEmail) : ResponseSignEmail {
        return service.postSignEmail(requestSignEmail)
    }

    override suspend fun getFirstDepartment(universityId: Int, filter: String) : ResponseFirstDepartment {
        return service.getFirstDepartment(universityId,filter)
    }

    override suspend fun postSignUp(requestSignUp: RequestSignUp) : ResponseSignUp {
        return service.postSignUp(requestSignUp)
    }

    override suspend fun postSignIn(requestSignIn: RequestSignIn) : ResponseSignIn {
        return service.postSignIn(requestSignIn)
    }
}