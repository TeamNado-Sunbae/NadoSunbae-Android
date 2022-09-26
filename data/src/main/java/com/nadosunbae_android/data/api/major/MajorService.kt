package com.nadosunbae_android.data.api.major

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.major.ResponseMajorListData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MajorService {
    //학과 리스트 조회
    @GET("major/university/{universityId}")
    suspend fun getMajorList(
        @Path("universityId") universityId : Int,
        @Query("filter") filter : String,
        @Query("exclude") exclude : String?,
        @Query("userId") userId : Int
    ) : Response<List<ResponseMajorListData>>
}