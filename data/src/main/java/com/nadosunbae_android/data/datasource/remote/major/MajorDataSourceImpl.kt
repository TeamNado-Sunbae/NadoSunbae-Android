package com.nadosunbae_android.data.datasource.remote.major

import com.nadosunbae_android.data.api.major.MajorService
import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.major.ResponseMajorListData
import javax.inject.Inject

class MajorDataSourceImpl @Inject constructor(private val service: MajorService) : MajorDataSource {

    override suspend fun getMajorList(
        universityId: Int,
        filter: String,
        exclude: String?,
        userId : Int
    ): Response<List<ResponseMajorListData>> {
        return service.getMajorList(universityId, filter, exclude,userId)
    }
}