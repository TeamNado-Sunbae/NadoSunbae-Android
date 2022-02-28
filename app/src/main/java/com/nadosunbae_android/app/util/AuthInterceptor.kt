package com.nadosunbae_android.app.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nadosunbae_android.app.di.NadoSunBaeApplication
import com.nadosunbae_android.domain.model.sign.RenewalTokenData
import com.nadosunbae_android.domain.usecase.sign.PostRenewalTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject

class AuthInterceptor(
    val baseUrl: String
) : Interceptor {

    companion object {
        const val TAG = "okhttp"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d(TAG, "request : $request")
        Log.d(TAG, "request header : ${request.headers}")

        val response = chain.proceed(request)
        Log.d(TAG, "response : $response")
        Log.d(TAG, "response header: ${response.headers}")

        when (response.code) {
            // 토큰 만료
            401 -> {

                val appContext = NadoSunBaeApplication.context()
                val data = postRenewalData(appContext, chain)

                if (data.success) {      // access token 재발급 성공
                    val newToken = data.accesstoken
                    Log.d(TAG, "refresh token renewal : ${newToken}")
                    val newRequest = request.newBuilder()
                        .addHeader("accesstoken", newToken)
                        .build()
                    return chain.proceed(newRequest)
                }
                else {
                   ManageUtil.restartApp(appContext)        // 재발급 실패 -> 앱 재실행 후 로그인
                }


                /*
                val newToken = getNewToken(NadoSunBaeSharedPreference.getRefreshToken(NadoSunBaeApplication.context()))

                if (newToken != null) {
                    val newRequest = chain.request().newBuilder()
                        .addHeader("accesstoken", newToken)
                        .addHeader("Content-Type", "application/json")
                        .build()
                    return chain.proceed(newRequest)
                }
                else {
                    // 앱 재실행 -> 로그인 화면 이동
                    ManageUtil.restartApp(NadoSunBaeApplication.context())
                }


                 */

            }

        }

        return response
    }

    private fun Response.extractRenewalData(): RenewalTokenData {
        return Gson().fromJson(body?.string(), RenewalTokenData::class.java)
    }

    private fun postRenewalData(context: Context, chain: Interceptor.Chain): RenewalTokenData {

        val request = chain.request()

        val renewalUrl = request.url.newBuilder()
            .host(baseUrl+"auth/renewal/token")
            .build()
        val renewalRequest = request.newBuilder()
            .url(renewalUrl)
            .addHeader("Content-Type", "application/json")
            .addHeader("accesstoken", NadoSunBaeSharedPreference.getAccessToken(context))
            .addHeader("refreshtoken", NadoSunBaeSharedPreference.getRefreshToken(context))
            .build()

        val renewalResponse = chain.proceed(renewalRequest)
        return renewalResponse.extractRenewalData()
    }


    /*

    private fun getNewToken(refreshToken: String): String? {
        var newToken: String? = null

        runBlocking {
            launch {
                runCatching { postRenewalTokenUseCase(refreshToken) }

                    .onSuccess {
                        newToken = it.accesstoken
                        Log.d(TAG, "토큰 재발급 성공")
                    }
                    .onFailure {
                        it.printStackTrace()
                        Log.d(TAG, "토큰 재발급 실패")
                    }
            }
        }
        return newToken
    }


     */


}

