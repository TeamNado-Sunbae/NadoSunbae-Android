package com.nadosunbae_android.app.util

sealed class ResultWrapper<out T>{
    data class Success<out T>(val data : T) : ResultWrapper<T>()
    data class GenericError(val code : Int?, val message : String?) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}
