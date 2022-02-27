package com.nadosunbae_android.app.util

import android.util.Log
import com.nadosunbae_android.app.di.NadoSunBaeApplication
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

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
                val newToken = getNewToken(NadoSunBaeSharedPreference.getRefreshToken(NadoSunBaeApplication.context()))
                val newRequest = chain.request().newBuilder()
                    .addHeader("accesstoken", newToken)
                    .addHeader("Content-Type", "application/json")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private fun getNewToken(refreshToken: String): String {
        runBlocking {
            launch {
                runCatching {  }
                    .onSuccess {

                    }
                    .onFailure {

                    }
            }
        }
    }
}