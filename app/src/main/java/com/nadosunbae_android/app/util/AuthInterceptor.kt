package com.nadosunbae_android.app.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nadosunbae_android.app.di.NadoSunBaeApplication
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.app.util.ManageUtil.isServiceRunning
import com.nadosunbae_android.app.util.ManageUtil.restartApp
import com.nadosunbae_android.domain.model.sign.RenewalTokenData
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception

class AuthInterceptor(
    private val baseUrl: String
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

                response.close()

                val appContext = NadoSunBaeApplication.context()

                if (NadoSunBaeSharedPreference.getRefreshToken(appContext).isEmpty())     // refresh token 없으면 재발급 로직 실행 x -> 루프 방지
                    return response

                val data: RenewalTokenData? = postRenewalData(appContext, chain)       // access token 재발급

                if (data != null && data.success) {      // access token 재발급 성공
                    val newToken = data.accessToken
                    NadoSunBaeSharedPreference.setAccessToken(appContext, data.accessToken)     // 재발급 access token 저장
                    Log.d(TAG, "refresh token renewal : ${newToken}")
                    val newRequest = request.newBuilder()
                        .header("accesstoken", newToken)
                        .build()
                    return chain.proceed(newRequest)
                }
                else {
                    Log.d(TAG, "refresh renewal failed")
                    NadoSunBaeSharedPreference.removeAccessToken(appContext)        // 만료된 access token 제거
                    NadoSunBaeSharedPreference.removeRefreshToken(appContext)      // 만료된 refresh token 제거

                    appContext.run {
                        if (!isServiceRunning(SignInActivity::class.java.name))     // SignIn 액티비티 실행 여부 확인
                            restartApp()                                              // 재발급 실패 -> 앱 재실행 후 로그인
                    }
                }

            }

        }

        return response
    }
    
    private fun Response.extractRenewalData(): RenewalTokenData {
        try {
            val result = Gson().fromJson(body?.string(), ResponseRenewalToken::class.java)
            this.close()

            return RenewalTokenData(
                    status = result.status,
                    success = result.success,
                    accessToken = result.data.accesstoken
                )
        } catch (e: Exception) {
            throw e
        }
    }

    private fun postRenewalData(context: Context, chain: Interceptor.Chain): RenewalTokenData? {
        val newRequest = Request.Builder()
            .url("${baseUrl}auth/renewal/token")
            .method("POST", "".toRequestBody())
            .addHeader("Content-Type", "application/json")
            .addHeader("accesstoken", NadoSunBaeSharedPreference.getAccessToken(context))
            .addHeader("refreshtoken", NadoSunBaeSharedPreference.getRefreshToken(context))
            .build()

        return try {
            chain.proceed(newRequest).extractRenewalData()
        } catch (e: Exception) {
            null
        }
    }


}

