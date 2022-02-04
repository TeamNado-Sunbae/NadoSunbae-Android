package com.nadosunbae_android.repositoryimpl.sign

import com.nadosunbae_android.api.ApiService
import com.nadosunbae_android.datasource.remote.sign.SignDataSource
import com.nadosunbae_android.datasource.remote.sign.SignDataSourceImpl
import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.*
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class SignRepositoryImpl : SignRepository {
    val signDataSource : SignDataSource = SignDataSourceImpl()

    //닉네임 중복확인
    override suspend fun postSignNickname(
        requestSignNickname: RequestSignNickname,
        onResponse: (Response<ResponseSignNickname>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.postSignNickname(requestSignNickname, onResponse, onFailure)
    }

    //이메일 중복확인
    override suspend fun postSignEmail(
        requestSignEmail: RequestSignEmail,
        onResponse: (Response<ResponseSignEmail>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.postSignEmail(requestSignEmail, onResponse, onFailure)
    }

    //학과선택 bottomsheet
    override suspend fun getFirstDepartment(
        universityId: Int,
        filter: String,
        onResponse: (Response<ResponseFirstDepartment>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.getFirstDepartment(universityId, filter, onResponse, onFailure)
    }

    //회원가입
    override suspend fun postSignUp(
        requestSignUp: RequestSignUp,
        onResponse: (Response<ResponseSignUp>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return signDataSource.postSignUp(requestSignUp, onResponse, onFailure)
    }

    //로그인
    override suspend fun postSignIn(
        requestSignIn: RequestSignIn,
        onResponse: (Response<ResponseSignIn>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return ApiService.signService.postSignIn(requestSignIn).enqueueUtil(
            onResponse, onFailure
        )
    }
}