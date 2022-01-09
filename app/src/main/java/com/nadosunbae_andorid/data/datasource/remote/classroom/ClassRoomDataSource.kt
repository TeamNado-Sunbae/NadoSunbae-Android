package com.nadosunbae_andorid.data.datasource.remote.classroom

import com.nadosunbae_andorid.data.api.ApiService
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call
import retrofit2.Response

interface ClassRoomDataSource {

    fun getClassRoom(userId : String, userInfo : String,
    onResponse : (Response<ResponseClassRoomData>) -> Unit,
    onFailure: (Throwable) -> Unit)

}