package com.nadosunbae_andorid.data.datasource.remote.classroom

import com.nadosunbae_andorid.data.api.ApiService
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassRoomDataSourceImpl() : ClassRoomDataSource {

    override fun getClassRoom(
        userId: String,
        userInfo: String,
        onResponse: (Response<ResponseClassRoomData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.classRoomService.getClassRoom(userId, userInfo).enqueue(
            object : Callback<ResponseClassRoomData>{
                override fun onResponse(
                    call: Call<ResponseClassRoomData>,
                    response: Response<ResponseClassRoomData>
                ) {
                    onResponse(response)
                }

                override fun onFailure(call: Call<ResponseClassRoomData>, t: Throwable) {
                    onFailure(t)
                }
            }
        )
    }
}