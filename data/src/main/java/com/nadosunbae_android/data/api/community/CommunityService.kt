package com.nadosunbae_android.data.api.community

import com.nadosunbae_android.data.model.response.Response
import com.nadosunbae_android.data.model.response.community.ResponseCommunityMainData
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityService {


    @GET("POST")
    suspend fun getCommunityMain(
        @Query("majorId") majorId : String,
        @Query("filter") filter : String,
        @Query("sort") sort : String
    ) : Response<List<ResponseCommunityMainData>>
}