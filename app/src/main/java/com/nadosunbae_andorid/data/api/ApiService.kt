package com.nadosunbae_andorid.data.api

import com.nadosunbae_andorid.data.api.classroom.ClassRoomService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://asia-northeast3-nadosunbae-server.cloudfunctions.net/api"

    val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)


    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client.build())
        .build()




}