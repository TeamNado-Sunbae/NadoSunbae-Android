package com.nadosunbae_android.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.nadosunbae_android.api.ApiService
import com.nadosunbae_android.util.NadoSunBaeSharedPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(ApiService.BASE_URL)
            .client(get<OkHttpClient>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .run {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(get<Interceptor>())
                addInterceptor { chain ->
                    val request = chain.request()
                    Log.d("okhttp", "request : $request")
                    Log.d("okhttp", "request header : ${request.headers}")
                    val response = chain.proceed(request)
                    Log.d("okhttp", "response : $response")
                    Log.d("okhttp", "response header: ${response.headers}")
                    response
                }
                build()
            }
    }

    single<Interceptor> {
        Interceptor { chain ->
            with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader("accesstoken",
                        NadoSunBaeSharedPreference.getAccessToken(NadoSunBaeApplication.context()))
                    .addHeader("Content-Type", "application/json")
                    .build()
                proceed(newRequest)
            }
        }
    }



}