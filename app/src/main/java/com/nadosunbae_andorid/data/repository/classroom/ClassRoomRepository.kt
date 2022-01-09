package com.nadosunbae_andorid.data.repository.classroom

import com.nadosunbae_andorid.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_andorid.data.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call
import retrofit2.Response

interface ClassRoomRepository {

    fun getClassRoom(userId : String, userInfo : String,
                     onResponse : (Response<ResponseClassRoomData>) -> Unit,
                     onFailure: (Throwable) -> Unit)
}