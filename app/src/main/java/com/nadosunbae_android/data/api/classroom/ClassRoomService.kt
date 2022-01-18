package com.nadosunbae_android.data.api.classroom

import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClassRoomService {

    @GET("/classroom-post/{postTypeId}/major/{majorId}/list")
    fun getClassRoomMain(
        @Path("postTypeId") postTypeId : Int,
        @Path("majorId") majorId : Int,
        @Query("sort") sort : String = "recent"
    ) : Call<ResponseClassRoomMainData>

}
