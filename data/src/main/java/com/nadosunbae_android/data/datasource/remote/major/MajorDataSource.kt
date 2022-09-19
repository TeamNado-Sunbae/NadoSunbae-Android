package com.nadosunbae_android.data.datasource.remote.major

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.major.ResponseMajorListData

interface MajorDataSource {

    //학과 리스트 조회
    suspend fun getMajorList(universityId : String, filter : String, exclude : String?) : Response<List<ResponseMajorListData>>
}