package com.nadosunbae_android.data.api.sign

import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignService {
    @POST("auth/duplication-check/nickname")
    fun postSignNickname(
        @Body requestSignNickname: String
    ): Call<ResponseSignNickname>
}