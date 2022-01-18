package com.nadosunbae_android.data.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.nadosunbae_android.data.api.classroom.ClassRoomService
import com.nadosunbae_android.data.api.sign.SignService
import com.nadosunbae_android.data.api.review.ReviewService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiService {
    private const val BASE_URL = "https://asia-northeast3-nadosunbae-server.cloudfunctions.net/api/"

    private val interceptors = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val gson = GsonBuilder().setLenient().create()



    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient(AppInterceptor()))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    //공통헤더 달기 로직
    private fun provideOkHttpClient(
        interceptor : AppInterceptor
    ) : OkHttpClient = OkHttpClient.Builder()
        .run {
            interceptors
            addInterceptor(interceptor)
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

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain):
                Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("accesstoken","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiZW1haWwiOiJrdTJAa29yZWEuYWMua3IiLCJuaWNrbmFtZSI6Imt1MiIsImZpcmViYXNlSWQiOiJOT1ZDYktEUUlEY3IybmJsam9ZazhSSnJ0NTUyIiwiaWF0IjoxNjQyMzI5OTMwLCJleHAiOjE2NDQ5MjE5MzAsImlzcyI6Im5hZG9TdW5iYWUifQ.tiblhyT4Y9-DDH1KTwIm37wevbChJ-R8-ECSb3QpZUI")
                .addHeader("content-Type", "application/json")
                .build()
            proceed(newRequest)
        }
    }


    val classRoomService : ClassRoomService = retrofit.create(ClassRoomService::class.java)
    val signService : SignService = retrofit.create(SignService::class.java)
    val reviewService : ReviewService = retrofit.create(ReviewService::class.java)

}