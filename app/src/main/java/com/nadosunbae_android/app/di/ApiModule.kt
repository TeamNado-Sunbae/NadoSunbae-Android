package com.nadosunbae_android.app.di

import com.google.gson.GsonBuilder
import com.nadosunbae_android.app.util.AuthInterceptor
import com.nadosunbae_android.app.util.NadoSunBaeSharedPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_PROD = "https://asia-northeast3-nadosunbae-server.cloudfunctions.net/api/"
private const val BASE_URL_DEV = "https://asia-northeast3-nadosunbae-server-dev-90ac3.cloudfunctions.net/api/"

private const val BASE_URL = BASE_URL_PROD

val apiModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(BASE_URL)
            .client(get<OkHttpClient>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .run {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(get<Interceptor>())
                addInterceptor(AuthInterceptor(BASE_URL))
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
                    .addHeader("refreshToken",
                    NadoSunBaeSharedPreference.getRefreshToken(NadoSunBaeApplication.context()))
                    .build()
                proceed(newRequest)
            }
        }
    }
}