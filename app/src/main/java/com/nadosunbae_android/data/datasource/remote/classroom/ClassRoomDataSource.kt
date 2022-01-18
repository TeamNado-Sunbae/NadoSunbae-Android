package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import retrofit2.Response

interface ClassRoomDataSource {

    fun getClassRoomMain(
        postTypeId: Int, majorId: Int, sort: String = "recent",
        onResponse: (Response<ResponseClassRoomMainData>) -> Unit,
        onFailure : (Throwable) -> Unit)
}