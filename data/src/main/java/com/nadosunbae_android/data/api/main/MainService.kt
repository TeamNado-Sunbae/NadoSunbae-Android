package com.nadosunbae_android.data.api.main

import com.nadosunbae_android.data.model.response.major.ResponseMajorListData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {

    // 학과 목록 불러오기
    @GET("major/list/{universityId}")
    suspend fun getMajorList (
        @Path("universityId") universityId: Int,
        @Query("filter") filter: String = "all"
    ) : ResponseMajorListData

}