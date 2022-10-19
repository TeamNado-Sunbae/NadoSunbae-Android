package com.nadosunbae_android.app.di

import com.google.gson.GsonBuilder
import com.nadosunbae_android.app.BuildConfig
import com.nadosunbae_android.app.util.AuthInterceptor
import com.nadosunbae_android.app.util.NadoSunBaeSharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//TODO Data 계층으로 옮기기
const val BASE_URL = BuildConfig.API_KEY

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .build()
    }


    private fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .run {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(provideInterceptor())
                addInterceptor(AuthInterceptor(BASE_URL))
                build()
    }


   private  fun provideInterceptor() =
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