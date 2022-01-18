package com.nadosunbae_android.data.datasource.remote.classroom

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class ClassRoomDataSourceImpl() : ClassRoomDataSource {


    override fun getClassRoomMain(
        postTypeId: Int,
        majorId: Int,
        sort: String,
        onResponse: (Response<ResponseClassRoomMainData>) -> Unit,
        onFailure : (Throwable) -> Unit)
     {
        ApiService.classRoomService.getClassRoomMain(postTypeId, majorId, sort).enqueueUtil(
            onResponse, onFailure
        )
    }


}