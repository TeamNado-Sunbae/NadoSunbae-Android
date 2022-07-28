package com.nadosunbae_android.app.util

import com.google.gson.Gson
import com.nadosunbae_android.domain.model.network.ErrorBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
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


