package com.nadosunbae_android.data.api.main

import com.nadosunbae_android.data.model.response.sign.ResponseMajorData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {

    @GET("major/list/{universityId}")
    fun getMajorList (
        @Path("universityId") universityId: Int,
        @Query("filter") filter: String = "all"
    ) : Call<ResponseMajorData>

}