package com.nadosunbae_android.app.util

import android.util.Log
import com.google.gson.Gson
import com.nadosunbae_android.domain.model.network.ErrorBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.koin.android.ext.koin.ERROR_MSG
import org.koin.core.context.GlobalContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher : CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T>{
    return withContext(dispatcher){
        try{
            ResultWrapper.Success(apiCall.invoke())
        }catch (throwable : Throwable){
            when(throwable){
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorBody = Gson().fromJson(throwable.response()?.errorBody()?.string(), ErrorBody::class.java)
                    val message = errorBody.message
                    ResultWrapper.GenericError(code, message )
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }

}


