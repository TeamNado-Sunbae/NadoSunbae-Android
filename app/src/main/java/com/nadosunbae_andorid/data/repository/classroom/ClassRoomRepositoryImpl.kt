package com.nadosunbae_andorid.data.repository.classroom

import com.nadosunbae_andorid.data.datasource.remote.classroom.ClassRoomDataSource
import com.nadosunbae_andorid.data.datasource.remote.classroom.ClassRoomDataSourceImpl
import com.nadosunbae_andorid.data.model.response.classroom.ResponseClassRoomData
import retrofit2.Call

class ClassRoomRepositoryImpl : ClassRoomRepository {
    val classRoomDataSource = ClassRoomDataSourceImpl()


    override fun getClassRoom(userId: String, userInfo: String): Call<ResponseClassRoomData> {
        return classRoomDataSource.getClassRoom(userId, userInfo)
    }
}