package com.nadosunbae_android.util

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <ResponseType> Call<ResponseType>.enqueueUtil(
    onResponse: (Response<ResponseType>) -> Unit,
    onFailure: (Throwable) -> Unit
) {
    this.enqueue(object : Callback<ResponseType> {
        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            onResponse(response)
        }

        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            onFailure(t)
        }
    })
}