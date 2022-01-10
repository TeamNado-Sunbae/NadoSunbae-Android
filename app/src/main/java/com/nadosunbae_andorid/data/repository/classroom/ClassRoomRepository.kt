package com.nadosunbae_andorid.data.repository.classroom

import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call

interface ClassRoomRepository {

    fun getClassRoom(userId : String, userInfo : String) : Call<ResponseClassRoomData>
}