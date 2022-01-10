package com.nadosunbae_andorid.data.datasource.remote.classroom

import com.nadosunbae_andorid.data.api.ApiService
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassRoomDataSourceImpl() : ClassRoomDataSource {

    override fun getClassRoom(userId: String, userInfo: String): Call<ResponseClassRoomData> {
        return ApiService.classRoomService.getClassRoom(userId, userInfo)
    }
}