package com.nadosunbae_andorid.data.datasource.remote.classroom

import com.nadosunbae_andorid.data.api.ApiService
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call

interface ClassRoomDataSource {

    fun getClassRoom(userId : String, userInfo : String) : Call<ResponseClassRoomData>

}