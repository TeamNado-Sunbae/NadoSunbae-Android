package com.nadosunbae_andorid.data.repository.classroom

import androidx.lifecycle.MutableLiveData
import com.nadosunbae_andorid.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_andorid.data.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call
import retrofit2.Response

class ClassRoomRepositoryImpl : ClassRoomRepository {
    val classRoomDataSource : ClassRoomDataSource = ClassRoomDataSourceImpl()


    override fun getClassRoom(
        userId: String,
        userInfo: String,
        onResponse: (Response<ResponseClassRoomData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        return classRoomDataSource.getClassRoom(userId,userInfo,onResponse,onFailure)
    }
}