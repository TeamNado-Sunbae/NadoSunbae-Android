package com.nadosunbae_andorid.data.api.classroom

import com.nadosunbae_andorid.data.model.request.classroom.RequestClassRoomData
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call
import retrofit2.http.*

interface ClassRoomService {

    @GET("/class/room/{userId}")
    fun getClassRoom(
        @Path("userId") userId: String,
        @Query("userInfo")  userInfo : String
    ) : Call<ResponseClassRoomData>

    @POST("/class/room")
    fun postClassRoom(
        @Body body : RequestClassRoomData
    ) : Call<ResponseClassRoomData>
}
